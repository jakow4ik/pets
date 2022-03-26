package by.dro.pets.di

import by.dro.pets.data.repositories.dogs.DogBookmarkStorage
import by.dro.pets.data.repositories.dogs.DogDataStore
import by.dro.pets.data.repositories.dogs.DogRepositoryImpl
import by.dro.pets.data.repositories.dogs.FirebaseDogDataStore
import by.dro.pets.domain.repositories.DogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDogBookmarkStorage(): DogBookmarkStorage {
        return DogBookmarkStorage()
    }

    @Provides
    @Singleton
    fun provideDogDataStore(): DogDataStore {
        return FirebaseDogDataStore()
    }

    @Provides
    @Singleton
    fun provideDogRepository(
        dogDataStore: DogDataStore,
        dogBookmarkStorage: DogBookmarkStorage,
    ): DogRepository {
        return DogRepositoryImpl(
            dogDataStore = dogDataStore,
            bookmarkStorage = dogBookmarkStorage
        )
    }
}