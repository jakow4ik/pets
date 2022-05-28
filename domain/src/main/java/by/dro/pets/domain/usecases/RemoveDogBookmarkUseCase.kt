package by.dro.pets.domain.usecases

import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.repositories.DogRepository

class RemoveDogBookmarkUseCase(private val repository: DogRepository) : AsyncUseCase<Dog, Unit> {

    override suspend fun execute(param: Dog) = repository.removeBookmark(param)
}