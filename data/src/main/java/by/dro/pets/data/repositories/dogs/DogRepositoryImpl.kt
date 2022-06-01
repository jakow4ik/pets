package by.dro.pets.data.repositories.dogs

import by.dro.pets.data.mappers.toDomainModel
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.repositories.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DogRepositoryImpl(
    private val bookmarkDataStore: BookmarkDataStore,
    private val dogDataStore: DogDataStore,
) : DogRepository {
    override fun getDogs(): Flow<List<Dog>> {
        return dogDataStore.getDogs().combine(bookmarkDataStore.getAll()) { dogsMap, bookmarks ->
            dogsMap.values.map { dog ->
                dog.toDomainModel(bookmarks.contains(dog.uid))
            }
        }
    }

    override fun getBookmarks(): Flow<List<Dog>> {
        return getDogs().map { list ->
            list.filter { it.isBookmarked }
        }
    }

    override suspend fun addBookmark(dog: Dog) {
        bookmarkDataStore.add(dog.uid)
    }

    override suspend fun removeBookmark(dog: Dog) {
        bookmarkDataStore.remove(dog.uid)
    }
}