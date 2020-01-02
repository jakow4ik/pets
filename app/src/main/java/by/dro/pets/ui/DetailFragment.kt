package by.dro.pets.ui

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment

import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21){
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = arguments?.getString(PetsListFragment.ARG_UID, "")
        Log.d("kkk", "uid2 = $uid")
        val pet = PetsViewModel.data.value?.get(uid)
        if (pet != null) updateUi(pet)
    }

    private fun updateUi(pet: Pet) {
        Log.d("kkk", "updateUi - uid = ${pet.uid} \n sdk = ${Build.VERSION.SDK_INT}")

        imageView2.also {

            if (Build.VERSION.SDK_INT >= 21)
                it.transitionName = pet.uid

            Glide.with(this)
                .load(pet.titleImg)
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(it)
        }

    }


}
