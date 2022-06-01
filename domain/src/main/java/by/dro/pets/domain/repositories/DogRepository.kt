package by.dro.pets.domain.repositories

import by.dro.pets.domain.entities.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {

    fun getDogs(): Flow<List<Dog>>

    fun getBookmarks(): Flow<List<Dog>>

    suspend fun addBookmark(dog: Dog)

    suspend fun removeBookmark(dog: Dog)
}