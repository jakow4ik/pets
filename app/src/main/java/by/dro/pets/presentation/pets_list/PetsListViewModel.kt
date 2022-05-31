package by.dro.pets.presentation.pets_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.usecases.AddDogBookmarkUseCase
import by.dro.pets.domain.usecases.GetDogsUseCase
import by.dro.pets.domain.usecases.RemoveDogBookmarkUseCase
import by.dro.pets.util.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
open class PetsListViewModel @Inject constructor(
    getDogsUseCase: GetDogsUseCase,
    private val addDogBookmark: AddDogBookmarkUseCase,
    private val removeDogBookmark: RemoveDogBookmarkUseCase,
) : ViewModel() {

    private val _search = MutableStateFlow(String.EMPTY)
    val dogsList = getDogsUseCase.execute(Unit)
        .combine(_search) { dogs, searchText ->
            if (searchText.isEmpty()) dogs
            else dogs.filter { dog ->
                dog.name.contains(searchText, ignoreCase = true)
                        || dog.nameInternational.contains(searchText, ignoreCase = true)
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onBookmarkClicked(dog: Dog) {
        viewModelScope.launch {
            if (dog.isBookmarked) {
                removeDogBookmark.execute(dog)
            } else {
                addDogBookmark.execute(dog)
            }
        }
    }

    fun onSearchTextChanged(text: CharSequence?) {
        _search.value = (text?.toString() ?: String.EMPTY).trim()
    }
}
