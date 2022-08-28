package by.dro.pets.data.mappers

import by.dro.pets.data.entities.DogModel
import by.dro.pets.data.entities.SectionItemModel
import by.dro.pets.data.entities.SectionModel
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.entities.Section
import by.dro.pets.domain.entities.SectionItem

fun DogModel.toDomainModel(isBookmarked: Boolean): Dog {
    return Dog(
        uid = uid.orEmpty(),
        name = name.orEmpty(),
        titleImg = title_img.orEmpty(),
        description = description.orEmpty().replace("\\n", "\n"),
        popularityRating = popularity_rating ?: 0.0,
        fciNumber = fci_number ?: 0,
        sections = sections?.toDomainSections() ?: emptyList(),
        isBookmarked = isBookmarked,
    )
}

private fun List<SectionModel>.toDomainSections(): List<Section> {
    return map {
        Section(
            title = it.title.orEmpty(),
            items = it.items?.toDomainSectionItems() ?: emptyList()
        )
    }
}

private fun List<SectionItemModel>.toDomainSectionItems(): List<SectionItem> {
    return map {
        SectionItem(
            title = it.title.orEmpty(),
            body = it.body.orEmpty().replace("\\n", "\n")
        )
    }
}