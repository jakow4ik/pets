package by.dro.pets.presentation.pets_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.usecases.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PetsListViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsUseCase,
) : ViewModel() {

    val dogs: StateFlow<List<Dog>> = getDogsUseCase
        .execute(Unit)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}