package by.dro.pets.domain.usecases

interface AsyncUseCase<in P, out T> {

    suspend fun execute(param: P): T
}