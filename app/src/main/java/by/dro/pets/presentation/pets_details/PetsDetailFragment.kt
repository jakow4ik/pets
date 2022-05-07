package by.dro.pets.presentation.pets_details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.RatingBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.dro.pets.Config
import by.dro.pets.R
import by.dro.pets.databinding.FragmentDetailBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.base.BaseFragment
import by.dro.pets.util.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetsDetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: PetsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun initView(vb: FragmentDetailBinding) {
//        vb.toolbar.inflateMenu(R.menu.pet_detail)
//        vb.toolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.action_share -> share(viewModel.dog.value)
//                else -> App.instance.toast("Unknown option")
//            }
//            true
//        }
        vb.toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_24px)
        vb.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dog.collect {
                    updateUi(it)
                }
            }
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
        Log.d("kkk", "updateUi - uid = ${pet.uid} \n sdk = ${Build.VERSION.SDK_INT}")
        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
            binding.petsImage.transitionName =
                String.format(getString(R.string.transition_image, pet.uid))
            binding.petsName.transitionName =
                String.format(getString(R.string.transition_name, pet.uid))
        }
        postponeEnterTransition()
        binding.petsImage.load(pet.titleImg) {
            startPostponedEnterTransition()
        }
        binding.toolbar.title = pet.name
        binding.petsName.text = pet.name
        binding.nameEn.text = pet.nameInternational
        binding.description.text = pet.description
        binding.standartNumber.text = pet.standartNumber
        binding.country.text = pet.country
        binding.using.text = pet.using
        binding.size.text = pet.size
        binding.weight.text = pet.weight
        binding.wool.text = pet.wool
        binding.color.text = pet.color
        binding.character.text = pet.character
        binding.care.text = pet.care
        binding.lifespan.text = pet.lifespan
        binding.problems.text = pet.problems

        setRatingBar(binding.popularityRating, pet.popularityRating)
        setRatingBar(binding.trainingRating, pet.trainingRating)
        setRatingBar(binding.sizeRating, pet.sizeRating)
        setRatingBar(binding.mindRating, pet.mindRating)
        setRatingBar(binding.protectionRating, pet.protectionRating)
        setRatingBar(binding.childrenRating, pet.childrenRating)
        setRatingBar(binding.dexterityRating, pet.dexterityRating)
        setRatingBar(binding.moltRating, pet.moltRating)
    }

    private fun setRatingBar(bar: RatingBar, value: Int?) {
        if ((value ?: 0) >= 0) {
            bar.visibility = View.VISIBLE
            bar.rating = (value ?: 0).toFloat()
        } else {
            bar.visibility = View.INVISIBLE
        }
    }
}