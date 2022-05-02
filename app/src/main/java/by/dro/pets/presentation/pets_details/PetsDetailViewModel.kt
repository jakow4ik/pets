package by.dro.pets.presentation.pets_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.usecases.GetDogsUseCase
import by.dro.pets.presentation.pets_list.PetsListFragment.Companion.ARG_UID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PetsDetailViewModel @Inject constructor(
    getDogsUseCase: GetDogsUseCase,
    args: SavedStateHandle
) : ViewModel() {

    val dog: StateFlow<Dog> = getDogsUseCase
        .execute(Unit)
        .map { dogs ->
            dogs.find {
                it.uid == args.get(ARG_UID)
            } ?: Dog.EMPTY
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Dog.EMPTY)

}