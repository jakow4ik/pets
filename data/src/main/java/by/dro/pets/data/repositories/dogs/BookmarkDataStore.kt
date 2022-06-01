package by.dro.pets.data.repositories.dogs

import kotlinx.coroutines.flow.Flow

interface BookmarkDataStore {

    suspend fun add(id: String)

    suspend fun remove(id: String)

    fun getAll(): Flow<Set<String>>
}