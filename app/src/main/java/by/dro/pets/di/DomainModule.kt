package by.dro.pets.di

import by.dro.pets.domain.repositories.DogRepository
import by.dro.pets.domain.usecases.AddDogBookmarkUseCase
import by.dro.pets.domain.usecases.GetBookmarksDogsUseCase
import by.dro.pets.domain.usecases.GetDogsUseCase
import by.dro.pets.domain.usecases.RemoveDogBookmarkUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddDogBookmarkUseCase(dogRepository: DogRepository): AddDogBookmarkUseCase {
        return AddDogBookmarkUseCase(dogRepository)
    }

    @Provides
    fun provideRemoveDogBookmarkUseCase(dogRepository: DogRepository): RemoveDogBookmarkUseCase {
        return RemoveDogBookmarkUseCase(dogRepository)
    }

    @Provides
    fun provideGetBookmarksDogsUseCase(dogRepository: DogRepository): GetBookmarksDogsUseCase {
        return GetBookmarksDogsUseCase(dogRepository)
    }

    @Provides
    fun provideGetDogsUseCase(dogRepository: DogRepository): GetDogsUseCase {
        return GetDogsUseCase(dogRepository)
    }
}