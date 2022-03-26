package by.dro.pets.domain.usecases

import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.repositories.DogRepository

class BookmarkDogUseCase(private val repository: DogRepository) : UseCase<Dog, Unit> {

    override fun execute(param: Dog) = repository.bookmark(param)
}