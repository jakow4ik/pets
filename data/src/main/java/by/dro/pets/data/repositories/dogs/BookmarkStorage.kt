package by.dro.pets.data.repositories.dogs

interface BookmarkStorage {

    fun add(id: String)

    fun remove(id: String)

    fun isBookmark(id: String): Boolean
}