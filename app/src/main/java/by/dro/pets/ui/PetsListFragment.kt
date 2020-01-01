package by.dro.pets.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View

import by.dro.pets.R
import by.dro.pets.data.ListPets
import by.dro.pets.data.Pet
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_pets_list.*

class PetsListFragment : Fragment(R.layout.fragment_pets_list) {

    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().reference

        button.setOnClickListener {
            setData()
        }

        database.child("pets").child("dogs").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val pets = p0.getValue(ListPets::class.java)
                Log.d("kkk", "pets - ${pets?.pets?.size}")
            }

        })

    }

    private fun setData(){
        Log.d("kkk", "0 + $database")
        val pet = Pet()
        pet.name = "korgi"
        pet.uid = database.child("pets").child("dogs").push().key
        database.child("pets").child("dogs").child(pet.uid!!).setValue(pet)
        Log.d("kkk", "1 + ${pet.uid}")
    }
}
