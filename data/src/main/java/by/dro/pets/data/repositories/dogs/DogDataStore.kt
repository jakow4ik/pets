package by.dro.pets.data.repositories.dogs

import by.dro.pets.data.entities.DogModel
import kotlinx.coroutines.flow.Flow

interface DogDataStore {
    fun getDogs(): Flow<Map<String, DogModel>>
}