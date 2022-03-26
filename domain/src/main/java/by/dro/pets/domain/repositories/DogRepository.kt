package by.dro.pets.domain.repositories

import by.dro.pets.domain.entities.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {

    fun getDogs(): Flow<Map<String, Dog>>

    fun getBookmarks(): Flow<Map<String, Dog>>

    fun bookmark(dog: Dog)

    fun unbookmark(dog: Dog)
}