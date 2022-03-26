package by.dro.pets.data.repositories.dogs

import by.dro.pets.data.ListPets
import kotlinx.coroutines.flow.Flow

interface DogDataStore {
    fun getDogs(): Flow<ListPets>
}