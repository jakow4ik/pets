package by.dro.pets.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.usecases.GetBookmarksDogsUseCase
import by.dro.pets.domain.usecases.RemoveDogBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarksDogsUseCase: GetBookmarksDogsUseCase,
    private val removeDogBookmarkUseCase: RemoveDogBookmarkUseCase,
) : ViewModel() {

    val bookmarkDogs: Flow<List<Dog>> = getBookmarksDogsUseCase.execute(Unit)

    fun onBookmarkClicked(dog: Dog) {
        viewModelScope.launch {
            removeDogBookmarkUseCase.execute(dog)
        }
    }
}