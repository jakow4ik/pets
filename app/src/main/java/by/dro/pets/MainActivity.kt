package by.dro.pets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import by.dro.pets.data.ListPets
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import by.dro.pets.ui.PetsListFragment
import com.google.firebase.database.*
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

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

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { link ->
                Log.d("MainActivityKKK", "${link?.link}")
//                findNavController(R.id.fragment).handleDeepLink(Intent().apply {
//                    data = link?.link
//
//                })
                val arguments = Bundle()
                arguments.putString(PetsListFragment.ARG_UID, link?.link?.getQueryParameter("uid"))
                if (link?.link != null)
                findNavController(R.id.fragment).navigate(
                    R.id.action_petsListFragment_to_detailFragment,
                    arguments
                )

            }
    }
}
