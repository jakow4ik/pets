package by.dro.pets.data.repositories.dogs

import by.dro.pets.data.mappers.toDomainModel
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.repositories.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DogRepositoryImpl(
    private val bookmarkStorage: BookmarkStorage,
    private val dogDataStore: DogDataStore,
) : DogRepository {
    override fun getDogs(): Flow<List<Dog>> {
        return dogDataStore.getDogs().map { listPets ->
            listPets.map.values.map {
                it.toDomainModel()
            }
        }
    }

    override fun getBookmarks(): Flow<List<Dog>> {
        return getDogs().map { listDogs ->
            listDogs.filter {
                bookmarkStorage.isBookmark(it.uid)
            }
        }
    }

    override fun bookmark(dog: Dog) {
        bookmarkStorage.add(dog.uid)
    }

    override fun unbookmark(dog: Dog) {
        bookmarkStorage.remove(dog.uid)
    }
}