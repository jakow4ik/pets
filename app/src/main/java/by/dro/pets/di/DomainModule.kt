package by.dro.pets.di

import by.dro.pets.domain.repositories.DogRepository
import by.dro.pets.domain.usecases.BookmarkDogUseCase
import by.dro.pets.domain.usecases.GetBookmarksDogsUseCase
import by.dro.pets.domain.usecases.GetDogsUseCase
import by.dro.pets.domain.usecases.UnbookmarkDogUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideBookmarkDogUseCase(dogRepository: DogRepository): BookmarkDogUseCase {
        return BookmarkDogUseCase(dogRepository)
    }

    @Provides
    fun provideGetBookmarksDogsUseCase(dogRepository: DogRepository): GetBookmarksDogsUseCase {
        return GetBookmarksDogsUseCase(dogRepository)
    }

    @Provides
    fun provideGetDogsUseCase(dogRepository: DogRepository): GetDogsUseCase {
        return GetDogsUseCase(dogRepository)
    }

    @Provides
    fun provideUnbookmarkDogUseCase(dogRepository: DogRepository): UnbookmarkDogUseCase {
        return UnbookmarkDogUseCase(dogRepository)
    }
}