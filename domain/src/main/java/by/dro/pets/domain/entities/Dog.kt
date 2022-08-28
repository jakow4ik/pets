package by.dro.pets.domain.entities

data class Dog(
    val uid: String,
    val name: String,
    val titleImg: String,
    val description: String,
    val popularityRating: Double,
    val fciNumber: Int,
    val sections: List<Section>,
    val isBookmarked: Boolean,
) {
    companion object {
        val EMPTY = Dog(
            uid = "",
            name = "",
            titleImg = "",
            description = "",
            popularityRating = 0.0,
            fciNumber = 0,
            sections = emptyList(),
            isBookmarked = false,
        )
    }
}

data class Section(
    val title: String,
    val items: List<SectionItem>,
)

data class SectionItem(
    val title: String,
    val body: String,
)