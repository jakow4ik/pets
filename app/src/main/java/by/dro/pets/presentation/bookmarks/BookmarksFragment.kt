package by.dro.pets.presentation.bookmarks

import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.dro.pets.R
import by.dro.pets.databinding.FragmentBookmarksBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.base.BaseFragment
import by.dro.pets.presentation.pets_list.PetsAdapter
import by.dro.pets.presentation.pets_list.PetsListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksFragment :
    BaseFragment<FragmentBookmarksBinding>(FragmentBookmarksBinding::inflate) {

    private val viewModel: BookmarksViewModel by viewModels()

    private val adapter = PetsAdapter(object : PetsAdapter.PetClickListener {
        override fun onPetClicked(pet: Dog?, imageView: ImageView, textView: TextView) {
            val extras = FragmentNavigatorExtras(
                imageView to getString(R.string.transition_image, pet?.uid),
                textView to getString(R.string.transition_name, pet?.uid)
            )
            findNavController().navigate(
                R.id.action_bookmarksFragment_to_detailFragment,
                bundleOf(PetsListFragment.ARG_UID to pet?.uid),
                null,
                extras
            )
        }

        override fun onBookmarkClicked(pet: Dog?) {
            viewModel.onBookmarkClicked(pet!!)
        }
    })

    override fun initView(vb: FragmentBookmarksBinding) {
        binding.bookmarksRecycler.adapter = adapter
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookmarkDogs.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}