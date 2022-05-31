package by.dro.pets.presentation.pets_list


import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.dro.pets.R
import by.dro.pets.databinding.FragmentPetsListBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.about.AboutFragment
import by.dro.pets.presentation.base.BaseFragment
import by.dro.pets.util.collectOnStart
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class PetsListFragment : BaseFragment<FragmentPetsListBinding>(FragmentPetsListBinding::inflate),
    MaterialSearchBar.OnSearchActionListener {

    protected open val viewModel: PetsListViewModel by viewModels()

    protected open val detailFragmentNavigation: Int = R.id.action_petsListFragment_to_detailFragment

    companion object {
        const val ARG_UID = "ARG_UID"
    }

    private val petsAdapter = PetsAdapter(object : PetsAdapter.PetClickListener {
        override fun onPetClicked(pet: Dog?, imageView: ImageView, textView: TextView) {

            if (binding.searchBar.isSearchEnabled)
                binding.searchBar.disableSearch()

            val extras = FragmentNavigatorExtras(
                imageView to getString(R.string.transition_image, pet?.uid),
                textView to getString(R.string.transition_name, pet?.uid)
            )
            findNavController().navigate(
                detailFragmentNavigation,
                bundleOf(ARG_UID to pet?.uid),
                null,
                extras
            )
        }

        override fun onBookmarkClicked(pet: Dog?) {
            viewModel.onBookmarkClicked(pet ?: return)
        }
    })

    override fun initView() {
        initSearchBar()
        initRecycler()
        collectOnStart(viewModel.dogsList) {
            petsAdapter.submitList(it)
        }
        postponeEnterTransition()
    }

    override fun onResume() {
        super.onResume()
        if (binding.searchBar.text.isNotEmpty()) {
            binding.searchBar.performClick()
        }
    }

    private fun initSearchBar() = binding.searchBar.apply {
        setNavButtonEnabled(true)
        setOnSearchActionListener(this@PetsListFragment)
        setMaxSuggestionCount(0)
        setHint(getString(android.R.string.search_go))
        setPlaceHolder(getString(R.string.app_name))
        addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                viewModel.onSearchTextChanged(s)
        })
    }

    private fun initRecycler() = binding.recycler.apply {
        adapter = petsAdapter
        doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> AboutFragment.show(parentFragmentManager)
            MaterialSearchBar.BUTTON_SPEECH -> Unit
            MaterialSearchBar.BUTTON_BACK -> binding.searchBar.disableSearch()
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) = Unit

    override fun onSearchConfirmed(text: CharSequence?) = Unit
}

