package by.dro.pets.presentation.pets_details

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.RatingBar
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import by.dro.pets.R
import by.dro.pets.databinding.FragmentDetailBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.base.BaseFragment
import by.dro.pets.util.collectOnStart
import by.dro.pets.util.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsDetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: PetsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun initView() {
//        vb.toolbar.inflateMenu(R.menu.pet_detail)
//        vb.toolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.action_share -> share(viewModel.dog.value)
//                else -> App.instance.toast("Unknown option")
//            }
//            true
//        }
        binding.toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_24px)
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        collectOnStart(viewModel.dog) { updateUi(it) }
        binding.petsDetailBookmark.setOnClickListener { viewModel.onBookmarkClicked() }
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
            petsImage.transitionName = String.format(getString(R.string.transition_image, pet.uid))
            petsName.transitionName = String.format(getString(R.string.transition_name, pet.uid))
            postponeEnterTransition()
            petsImage.load(pet.titleImg) { startPostponedEnterTransition() }
            toolbar.title = pet.name
            petsName.text = pet.name
            nameEn.text = pet.nameInternational
            description.text = pet.description
            standartNumber.text = pet.standartNumber
            country.text = pet.country
            using.text = pet.using
            size.text = pet.size
            weight.text = pet.weight
            wool.text = pet.wool
            color.text = pet.color
            character.text = pet.character
            care.text = pet.care
            lifespan.text = pet.lifespan
            problems.text = pet.problems

            popularityRating.setRating(pet.popularityRating)
            trainingRating.setRating(pet.trainingRating)
            sizeRating.setRating(pet.sizeRating)
            mindRating.setRating(pet.mindRating)
            protectionRating.setRating(pet.protectionRating)
            childrenRating.setRating(pet.childrenRating)
            dexterityRating.setRating(pet.dexterityRating)
            moltRating.setRating(pet.moltRating)

            petsDetailBookmark.setImageResource(
                if (pet.isBookmarked) R.drawable.ic_bookmark_enable else R.drawable.ic_bookmark_disable
            )
        }
    }

    private fun RatingBar.setRating(value: Int?) {
        rating = (value ?: 0).toFloat()
        isInvisible = (value ?: 0) < 0
    }
}
