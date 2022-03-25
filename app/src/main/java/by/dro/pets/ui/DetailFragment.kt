package by.dro.pets.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.dro.pets.Config
import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import by.dro.pets.databinding.FragmentDetailBinding
import by.dro.pets.util.load


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private var uid: String? = null
    private var pet: Pet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }

//        val intent = activity?.intent
//        val action: String? = intent?.action
//        val data: Uri? = intent?.data


//        Log.d("DetailFragment", "action - $action")
//        Log.d("DetailFragment", "uri - $data")
//        Log.d("DetailFragment", "type - ${data?.getQueryParameter("type")}")
        PetsViewModel.data.observe(this, Observer { map ->
            pet = map?.get(uid)
            pet?.let { updateUi(it) }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        binding.toolbar.inflateMenu(R.menu.pet_detail)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> share(pet)
//                else -> App.instance.toast("Unknown option")
            }
            true
        }


        binding.toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_24px)
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        uid = arguments?.getString(PetsListFragment.ARG_UID, "")
        Log.d("kkk", "uid2 = $uid")
        pet = PetsViewModel.data.value?.get(uid)
        pet?.let { updateUi(it) }


    }

    private fun share(pet: Pet?) {

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

    private fun updateUi(pet: Pet) {
        Log.d("kkk", "updateUi - uid = ${pet.uid} \n sdk = ${Build.VERSION.SDK_INT}")

        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
            binding.titleImg.transitionName = String.format(getString(R.string.transition_image, pet.uid))
            binding.name.transitionName = String.format(getString(R.string.transition_name, pet.uid))
        }

        postponeEnterTransition()
        binding.titleImg.load(pet.titleImg ?: "") {
            startPostponedEnterTransition()
        }

        binding.toolbar.title = pet.name
        binding.name.text = pet.name
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
