package by.dro.pets.presentation.pets_list


import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.dro.pets.Config
import by.dro.pets.R
import by.dro.pets.databinding.FragmentPetsListBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.about.AboutFragment
import by.dro.pets.presentation.base.BaseFragment
import com.mancj.materialsearchbar.MaterialSearchBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetsListFragment : BaseFragment<FragmentPetsListBinding>(FragmentPetsListBinding::inflate),
    MaterialSearchBar.OnSearchActionListener {

    private val viewModel: PetsListViewModel by viewModels()

    companion object {
        const val ARG_UID = "ARG_UID"
    }

    //    private lateinit var database: DatabaseReference

    private val adapter = PetsAdapter(object : PetsAdapter.PetClickListener {
        override fun onPetClicked(pet: Dog?, imageView: ImageView, textView: TextView) {

            if (binding.searchBar.isSearchEnabled)
                binding.searchBar.disableSearch()

            val extras = FragmentNavigatorExtras(
                imageView to getString(R.string.transition_image, pet?.uid),
                textView to getString(R.string.transition_name, pet?.uid)
            )

            val arguments = Bundle()
            arguments.putString(ARG_UID, pet?.uid)

            if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
                findNavController().navigate(
                    R.id.action_petsListFragment_to_detailFragment,
                    arguments,
                    null,
                    extras
                )
            } else {
                findNavController().navigate(
                    R.id.action_petsListFragment_to_detailFragment,
                    arguments
                )
            }

        }

        override fun onBookmarkClicked(pet: Dog?) {
            viewModel.onBookmarkClicked(pet!!)
        }
    })

    override fun initView(vb: FragmentPetsListBinding) {
        vb.searchBar.setNavButtonEnabled(true)
        vb.searchBar.setOnSearchActionListener(this)
        vb.searchBar.setMaxSuggestionCount(0)
        vb.searchBar.setHint(getString(android.R.string.search_go))
        vb.searchBar.setPlaceHolder(getString(R.string.app_name))
        vb.searchBar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                search(s)
        })

//        database = FirebaseDatabase.getInstance().reference
//            .child("pets").child("dogs").child("ru")

        val linearLayout = LinearLayoutManager(context)
        vb.recycler.layoutManager = linearLayout
        vb.recycler.adapter = adapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogs.collect {
                    adapter.submitList(it)
                }
            }
        }
        postponeEnterTransition()
        vb.recycler.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun search(s: CharSequence?) {
        if (s == null || s == "") {
            adapter.submitList(viewModel.dogs.value)
            return
        }
        val result = viewModel.dogs.value.filter {
            it.name.contains(s, ignoreCase = true)
                    || it.nameInternational.contains(s, ignoreCase = true)
        }
        adapter.submitList(result)
    }

//    private fun setData() {
//        database.push()
//        Log.d("kkk", "0 + $database")
//        val pet = Pet()
//        pet.name = "korgi"
//        pet.uid = database.child("map").push().key
//        database.child("map").child(pet.uid!!).setValue(pet)
//        Log.d("kkk", "1 + ${pet.uid}")
//    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> {
                AboutFragment.show(parentFragmentManager)
//            setData()
            }
            MaterialSearchBar.BUTTON_SPEECH -> {}
            MaterialSearchBar.BUTTON_BACK -> {
                binding.searchBar.disableSearch()
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
//        Log.d("kkkl", enabled.toString())
    }

    override fun onSearchConfirmed(text: CharSequence?) {
//        Log.d("kkkl", text.toString())
    }
}

