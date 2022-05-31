package by.dro.pets.presentation.bookmarks

import by.dro.pets.domain.usecases.AddDogBookmarkUseCase
import by.dro.pets.domain.usecases.GetBookmarksDogsUseCase
import by.dro.pets.domain.usecases.RemoveDogBookmarkUseCase
import by.dro.pets.presentation.pets_list.PetsListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    getBookmarksDogsUseCase: GetBookmarksDogsUseCase,
    addDogBookmarkUseCase: AddDogBookmarkUseCase,
    removeDogBookmarkUseCase: RemoveDogBookmarkUseCase,
) : PetsListViewModel(getBookmarksDogsUseCase, addDogBookmarkUseCase, removeDogBookmarkUseCase)