package by.dro.pets.domain.repositories

import by.dro.pets.domain.entities.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {

    fun getDogs(): Flow<List<Dog>>

    fun getBookmarks(): Flow<List<Dog>>

    fun bookmark(dog: Dog)

    fun unbookmark(dog: Dog)
}