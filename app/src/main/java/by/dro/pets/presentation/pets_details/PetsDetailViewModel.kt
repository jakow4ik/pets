package by.dro.pets.presentation.pets_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.dro.pets.domain.entities.Dog
import by.dro.pets.domain.entities.SectionItem
import by.dro.pets.domain.usecases.AddDogBookmarkUseCase
import by.dro.pets.domain.usecases.GetDogsUseCase
import by.dro.pets.domain.usecases.RemoveDogBookmarkUseCase
import by.dro.pets.presentation.pets_list.PetsListFragment.Companion.ARG_UID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PetsDetailViewModel @Inject constructor(
    getDogsUseCase: GetDogsUseCase,
    args: SavedStateHandle,
    private val addDogBookmarkUseCase: AddDogBookmarkUseCase,
    private val removeDogBookmarkUseCase: RemoveDogBookmarkUseCase,
) : ViewModel() {

    private val _detail = MutableStateFlow(DescriptionData())
    val detail = _detail.asStateFlow()

    private val _sections = MutableStateFlow<List<DescriptionSection>>(emptyList())
    val sections = _sections.asStateFlow()

    val dog: StateFlow<Dog> = getDogsUseCase
        .execute(Unit)
        .map { dogs ->
            dogs.find { it.uid == args[ARG_UID] } ?: Dog.EMPTY
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Dog.EMPTY)

    init {
        viewModelScope.launch {
            getDogsUseCase.execute(Unit).collect { dogs ->
                dogs.find {
                    it.uid == args[ARG_UID]
                }?.also { dog ->
                    dog.popularityRating
                    _detail.emit(
                        DescriptionData(
                            descriptionText = dog.description,
                            popularityRating = dog.popularityRating,
                        )
                    )
                    _sections.emit(
                        dog.getDescriptionSection()
                    )
                }
            }
        }
    }

    fun onBookmarkClicked() {
        viewModelScope.launch {
            val pet = dog.value
            if (pet.isBookmarked) {
                removeDogBookmarkUseCase.execute(pet)
            } else {
                addDogBookmarkUseCase.execute(pet)
            }
        }
    }

    data class DescriptionSection(
        val title: String,
        val items: List<SectionItem>,
    )

    private fun Dog.getDescriptionSection(): List<DescriptionSection> {
        return sections.map {
            DescriptionSection(
                title = it.title,
                items = it.items,
            )
        }
    }
}
