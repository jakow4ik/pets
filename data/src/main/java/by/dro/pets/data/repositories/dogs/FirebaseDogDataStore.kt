package by.dro.pets.data.repositories.dogs

import androidx.annotation.Keep
import by.dro.pets.data.entities.DogModel
import com.google.firebase.database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FirebaseDogDataStore : DogDataStore {

    private val _dogs = MutableSharedFlow<Map<String, DogModel>>(replay = 1)

    init {
        FirebaseDatabase.getInstance().reference
            .child("pets").child("dogs").child("v2")
            .addValueEventListener(DogsEventListener(_dogs))
    }

    override fun getDogs(): Flow<Map<String, DogModel>> = _dogs.asSharedFlow()

    private class DogsEventListener(
        private val sharedFlow: MutableSharedFlow<Map<String, DogModel>>
    ) : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val pets = snapshot.getValue(PetsMapGenericTypeIndicator()) ?: emptyMap()
            sharedFlow.tryEmit(pets)
        }

        override fun onCancelled(error: DatabaseError) = Unit
    }

    @Keep
    private class PetsMapGenericTypeIndicator : GenericTypeIndicator<Map<String, DogModel>>()
}