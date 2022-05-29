package by.dro.pets.di

import android.content.Context
import by.dro.pets.data.repositories.dogs.BookmarkDataStore
import by.dro.pets.data.repositories.dogs.BookmarkDataStoreImpl
import by.dro.pets.data.repositories.dogs.DogDataStore
import by.dro.pets.data.repositories.dogs.DogRepositoryImpl
import by.dro.pets.data.repositories.dogs.FirebaseDogDataStore
import by.dro.pets.domain.repositories.DogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val DOG_BOOKMARK_DATA_STORE = "DOG_BOOKMARK_DATA_STORE"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDogBookmarkStorage(
        @ApplicationContext context: Context,
    ): BookmarkDataStore {
        return BookmarkDataStoreImpl(
            context = context,
            dataStoreName = DOG_BOOKMARK_DATA_STORE,
        )
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
        dogBookmarkStorage: BookmarkDataStore,
    ): DogRepository {
        return DogRepositoryImpl(
            dogDataStore = dogDataStore,
            bookmarkDataStore = dogBookmarkStorage,
        )
    }
}