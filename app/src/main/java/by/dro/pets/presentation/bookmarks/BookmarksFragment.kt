package by.dro.pets.presentation.bookmarks

import androidx.fragment.app.viewModels
import by.dro.pets.R
import by.dro.pets.presentation.pets_list.PetsListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : PetsListFragment() {

    override val viewModel: BookmarksViewModel by viewModels()

    override val detailFragmentNavigation: Int = R.id.action_bookmarksFragment_to_detailFragment
}