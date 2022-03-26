package by.dro.pets.domain.usecases

interface UseCase<in P, out T> {

    fun execute(param: P): T
}