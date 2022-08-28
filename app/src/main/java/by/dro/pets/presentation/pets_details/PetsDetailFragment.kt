package by.dro.pets.presentation.pets_details

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import by.dro.pets.R
import by.dro.pets.databinding.FragmentDetailBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.base.BaseFragment
import by.dro.pets.util.collectOnStart
import by.dro.pets.util.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class PetsDetailFragment :
    BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: PetsDetailViewModel by viewModels()

    private val descriptionAdapter = DescriptionAdapter()
    private val sectionAdapter = SectionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun initView() {
        binding.apply {
            detailsCloseIV.setOnClickListener { activity?.onBackPressed() }
            detailsBookmarkIV.setOnClickListener { viewModel.onBookmarkClicked() }
            detailsRV.adapter = ConcatAdapter(descriptionAdapter, sectionAdapter)
            detailsRV.addItemDecoration(getDividerItemDecoration())
        }
        collectOnStart(viewModel.dog) { updateUi(it) }
        collectOnStart(viewModel.sections) { sectionAdapter.submitList(it) }
        collectOnStart(viewModel.detail) { descriptionAdapter.setDescription(it) }
        collectOnStart(sectionAdapter.scrollToSection) { section ->
            if (section == SectionAdapter.INIT_POSITION) {
                binding.detailsRV.scrollToPosition(0)
                binding.root.transitionToStart()
            } else {
                delay(200)
                binding.detailsRV.scrollToPosition(section + descriptionAdapter.itemCount)
                binding.root.transitionToEnd()
            }
        }
    }

    private fun getDividerItemDecoration(): RecyclerView.ItemDecoration {
        return DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL,
        ).also { decoration ->
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)
                ?.let { decoration.setDrawable(it) }
        }
    }

    private fun share(pet: Dog?) {
        pet?.let { itPet ->
            val typePet = "dog"
            val link =
                "https://pets.dro.by/link/?link=https://pets.dro.by/app/?type%3D$typePet%26uid%3D${pet.uid}&apn=by.dro.pets"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${itPet.name}  -  $link")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun updateUi(pet: Dog) {
        binding.apply {
            detailsImgIV.transitionName = getString(R.string.transition_image, pet.uid)
            detailsPetNameTV.transitionName = getString(R.string.transition_name, pet.uid)
            detailsBookmarkIV.transitionName = getString(R.string.transition_bookmark, pet.uid)
            postponeEnterTransition()
            detailsImgIV.load(pet.titleImg) { startPostponedEnterTransition() }
            detailsPetNameTV.text = pet.name

            detailsBookmarkIV.setImageResource(
                if (pet.isBookmarked) R.drawable.ic_bookmark_enable else R.drawable.ic_bookmark_disable
            )
        }
    }
}
