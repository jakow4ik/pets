package by.dro.pets.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import kotlinx.android.synthetic.main.fragment_pets_list.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList


class PetsListFragment : Fragment(R.layout.fragment_pets_list) {

    private lateinit var database: DatabaseReference
    private var adapter = PetsAdapter(null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
            .child("pets").child("dogs").child("ru")

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        button.setOnClickListener {
            setData()
        }

        PetsViewModel.data.observe(
            this,
            Observer<Map<String, Pet>> {map ->
                Log.d("kkk", "--------------------------------------------")
               adapter.updateData(ArrayList(map.values))

            }
        )



    }

    private fun setData(){
        database.push()
        Log.d("kkk", "0 + $database")
        val pet = Pet()
        pet.name = "korgi"
        pet.uid = database.child("map").push().key
        database.child("map").child(pet.uid!!).setValue(pet)
        Log.d("kkk", "1 + ${pet.uid}")
    }
}

