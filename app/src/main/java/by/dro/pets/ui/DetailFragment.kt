package by.dro.pets.ui

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.dro.pets.Config

import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import by.dro.pets.util.load
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK){
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_24px)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        val uid = arguments?.getString(PetsListFragment.ARG_UID, "")
        Log.d("kkk", "uid2 = $uid")
        val pet = PetsViewModel.data.value?.get(uid)
        if (pet != null) updateUi(pet)

    }

    private fun updateUi(pet: Pet) {
        Log.d("kkk", "updateUi - uid = ${pet.uid} \n sdk = ${Build.VERSION.SDK_INT}")

        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
            titleImg.transitionName = String.format(getString(R.string.transition_image, pet.uid))
            name.transitionName = String.format(getString(R.string.transition_name, pet.uid))
        }

        postponeEnterTransition()
        titleImg.load(pet.titleImg ?: "") {
            startPostponedEnterTransition()
        }

        toolbar.title = pet.name
        name.text = pet.name
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


        popularityRating.rating = (pet.popularityRating ?: 0).toFloat()
        trainingRating.rating = (pet.trainingRating ?: 0).toFloat()
        sizeRating.rating = (pet.sizeRating ?: 0).toFloat()
        mindRating.rating = (pet.mindRating ?: 0).toFloat()
        protectionRating.rating = (pet.protectionRating ?: 0).toFloat()
        childrenRating.rating = (pet.childrenRating ?: 0).toFloat()
        dexterityRating.rating = (pet.dexterityRating ?: 0).toFloat()
        moltRating.rating = (pet.moltRating ?: 0).toFloat()

    }


}
