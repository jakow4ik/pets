package by.dro.pets.domain.usecases

import by.dro.pets.domain.repositories.DogRepository

class GetBookmarksDogsUseCase(private val repository: DogRepository) : GetDogsUseCase(repository) {

    override fun execute(param: Unit) = repository.getBookmarks()
}