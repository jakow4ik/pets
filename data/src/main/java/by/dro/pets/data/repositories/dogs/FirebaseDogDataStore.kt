package by.dro.pets.data.repositories.dogs

import android.util.Log
import by.dro.pets.data.ListPets
import com.google.firebase.database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FirebaseDogDataStore: DogDataStore {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        .child("pets").child("dogs").child("ru")

    private val _dogs = MutableSharedFlow<ListPets>(replay = 1)

    init {

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val pets = p0.getValue(ListPets::class.java) ?: return
//                liveData.value = pets?.map
                Log.d("kkk", "load data")
                _dogs.tryEmit(pets)
            }

        })

    }

    override fun getDogs(): Flow<ListPets> {
        return _dogs.asSharedFlow()
    }
}