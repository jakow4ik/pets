package by.dro.pets.presentation.pets_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.usecases.AddDogBookmarkUseCase
import by.dro.pets.domain.usecases.GetDogsUseCase
import by.dro.pets.domain.usecases.RemoveDogBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PetsListViewModel @Inject constructor(
    getDogsUseCase: GetDogsUseCase,
    private val addDogBookmark: AddDogBookmarkUseCase,
    private val removeDogBookmark: RemoveDogBookmarkUseCase,
) : ViewModel() {

    val dogs: StateFlow<List<Dog>> = getDogsUseCase
        .execute(Unit)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onBookmarkClicked(dog: Dog) {
        viewModelScope.launch {
            if (dog.isBookmark) {
                removeDogBookmark.execute(dog)
            } else {
                addDogBookmark.execute(dog)
            }
        }
    }
}