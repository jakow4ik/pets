package by.dro.pets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import by.dro.pets.data.ListPets
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var database: DatabaseReference
    private val liveData: MutableLiveData<MutableMap<String, Pet>> = PetsViewModel.data as MutableLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference
            .child("pets").child("dogs").child("ru")

        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val pets = p0.getValue(ListPets::class.java)
                liveData.value = pets?.map
            }

        })
    }
}
