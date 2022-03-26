package by.dro.pets.domain.usecases

import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.repositories.DogRepository
import kotlinx.coroutines.flow.Flow

class GetDogsUseCase(private val repository: DogRepository) : UseCase<Unit, Flow<List<Dog>>> {

    override fun execute(param: Unit) = repository.getDogs()
}