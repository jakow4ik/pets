package by.dro.pets.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import by.dro.pets.util.getContext
import kotlinx.android.synthetic.main.fragment_pets_list.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList


class PetsListFragment : Fragment(R.layout.fragment_pets_list) {

    companion object {
        const val ARG_UID = "ARG_UID"
    }


    private lateinit var database: DatabaseReference
    private val adapter = PetsAdapter(object : PetsAdapter.PetSelectedListener {
        override fun onPetSelected(pet: Pet?, imageView: ImageView, textView: TextView) {


            val extras = FragmentNavigatorExtras(
                imageView to String.format(getString(R.string.transition_image, pet?.uid)),
                textView to String.format(getString(R.string.transition_name, pet?.uid))
            )

            val arguments = Bundle()
            arguments.putString(ARG_UID, pet?.uid)
            findNavController().navigate(
                R.id.action_petsListFragment_to_detailFragment,
                arguments,
                null,
                extras
            )
            Log.d("kkk", "${pet?.uid}")

        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
            .child("pets").child("dogs").child("ru")

        val linearLayout = LinearLayoutManager(context)
        recycler.layoutManager = linearLayout
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(context, linearLayout.orientation))


        PetsViewModel.data.observe(
            this.viewLifecycleOwner,
            Observer<Map<String, Pet>> { adapter.petsList = ArrayList(it.values) }
        )

        postponeEnterTransition()
        recycler.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setData() {
        database.push()
        Log.d("kkk", "0 + $database")
        val pet = Pet()
        pet.name = "korgi"
        pet.uid = database.child("map").push().key
        database.child("map").child(pet.uid!!).setValue(pet)
        Log.d("kkk", "1 + ${pet.uid}")
    }
}

