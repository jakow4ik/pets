package by.dro.pets.ui

import android.content.Intent
import android.net.Uri
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
import by.dro.pets.util.load
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var uid :String? = null
    private var pet :Pet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK){
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

        toolbar.inflateMenu(R.menu.pet_detail)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> share(pet)
//                else -> App.instance.toast("Unknown option")
            }
            true
        }


        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_24px)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        uid = arguments?.getString(PetsListFragment.ARG_UID, "")
        Log.d("kkk", "uid2 = $uid")
        pet = PetsViewModel.data.value?.get(uid)
        pet?.let { updateUi(it) }


    }

    private fun share(pet: Pet?){

        pet?.let {itPet ->
            val  typePet = "dog"
            val link = "https://pets.dro.by/link/?link=https://pets.dro.by/app/?type%3D$typePet%26uid%3D${pet.uid}&apn=by.dro.pets"
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


        setRatingBar(popularityRating, pet.popularityRating)
        setRatingBar(trainingRating, pet.trainingRating)
        setRatingBar(sizeRating, pet.sizeRating)
        setRatingBar(mindRating, pet.mindRating)
        setRatingBar(protectionRating, pet.protectionRating)
        setRatingBar(childrenRating, pet.childrenRating)
        setRatingBar(dexterityRating, pet.dexterityRating)
        setRatingBar(moltRating, pet.moltRating)

    }

    private fun setRatingBar(bar: RatingBar, value: Int?){
        if ((value ?: 0) >= 0){
            bar.visibility = View.VISIBLE
            bar.rating = (value ?: 0).toFloat()
        }else{
            bar.visibility = View.INVISIBLE
        }
    }


}
