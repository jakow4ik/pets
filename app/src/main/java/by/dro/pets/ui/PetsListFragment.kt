package by.dro.pets.ui


import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.dro.pets.Config

import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.data.PetsViewModel
import kotlinx.android.synthetic.main.fragment_pets_list.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mancj.materialsearchbar.MaterialSearchBar
import java.util.ArrayList


class PetsListFragment : Fragment(R.layout.fragment_pets_list), MaterialSearchBar.OnSearchActionListener {

    companion object {
        const val ARG_UID = "ARG_UID"
    }

    private lateinit var database: DatabaseReference
    private var listPets: List<Pet>? = null

    private val adapter = PetsAdapter(object : PetsAdapter.PetSelectedListener {
        override fun onPetSelected(pet: Pet?, imageView: ImageView, textView: TextView) {

            if(searchBar.isSearchEnabled)
                searchBar.disableSearch()

            val extras = FragmentNavigatorExtras(
                imageView to String.format(getString(R.string.transition_image, pet?.uid)),
                textView to String.format(getString(R.string.transition_name, pet?.uid))
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
            } else{
                findNavController().navigate(
                    R.id.action_petsListFragment_to_detailFragment,
                    arguments
                )
            }

        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchBar.setNavButtonEnabled(true)
        searchBar.setOnSearchActionListener(this)
        searchBar.setMaxSuggestionCount(0)
        searchBar.setHint(getString(android.R.string.search_go))
        searchBar.setPlaceHolder(getString(R.string.app_name))
        searchBar.addTextChangeListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search(s)

            }
        })

        database = FirebaseDatabase.getInstance().reference
            .child("pets").child("dogs").child("ru")

        val linearLayout = LinearLayoutManager(context)
        recycler.layoutManager = linearLayout
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(context, linearLayout.orientation))


        PetsViewModel.data.observe(
            this.viewLifecycleOwner,
            Observer<Map<String, Pet>> {
                listPets = ArrayList(it.values)
                adapter.petsList = listPets

            }
        )

        postponeEnterTransition()
        recycler.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun search(s: CharSequence?){
        if (s == null || s == ""){
            adapter?.petsList = listPets
            return
        }

        val result = listPets?.filter { it.name?.contains(s, ignoreCase = true) ?: false }

        adapter?.petsList = result

    }

    private fun setData() {
        database.push()
        Log.d("kkk", "0 + $database")
        val pet = Pet()
        pet.name = "korgi"
        pet.uid = database.child("map").push().key
        database.child("map").child(pet.uid!!).setValue(pet)
        Log.d("kkk", "1 + ${pet.uid}")
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> {
                AboutFragment.show(parentFragmentManager)
//            setData()
            }
            MaterialSearchBar.BUTTON_SPEECH -> { }
            MaterialSearchBar.BUTTON_BACK -> {searchBar.disableSearch()}
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        Log.d("kkkl", enabled.toString())
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        Log.d("kkkl", text.toString())
    }

}

