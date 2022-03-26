package by.dro.pets.data.mappers

import by.dro.pets.data.DogModel
import by.dro.pets.domain.entities.Dog

fun DogModel.toDomainModel(): Dog {
    return Dog(
        uid = uid ?: "",
        name = name ?: "",
        nameInternational = nameInternational ?: "",
        titleImg = titleImg ?: "",
        description = description ?: "",
        popularityRating = popularityRating ?: 0,
        trainingRating = trainingRating ?: 0,
        sizeRating = sizeRating ?: 0,
        mindRating = mindRating ?: 0,
        protectionRating = protectionRating ?: 0,
        childrenRating = childrenRating ?: 0,
        dexterityRating = dexterityRating ?: 0,
        moltRating = moltRating ?: 0,
        standartNumber = standartNumber ?: "",
        country = country ?: "",
        using = using ?: "",
        size = size ?: "",
        weight = weight ?: "",
        wool = wool ?: "",
        color = color ?: "",
        character = character ?: "",
        care = care ?: "",
        lifespan = lifespan ?: "",
        problems = problems ?: "",
    )
}