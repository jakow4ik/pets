package by.dro.pets.presentation.pets_list

import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.Config
import by.dro.pets.R
import by.dro.pets.databinding.PetViewHolderBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.util.getContext
import by.dro.pets.util.load

class PetViewHolder(private val binding: PetViewHolderBinding, selectedListener: PetsAdapter.PetSelectedListener?) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            selectedListener?.onPetSelected(pet, binding.petsViewHolderImageIV, binding.petsViewHolderNameTV)
        }
    }

    private var pet: Dog? = null

    fun bind(pet: Dog) {
        this.pet = pet

        binding.petsViewHolderNameTV.text = pet.name
        binding.petsViewHolderTypeTV.text = pet.country
        binding.petsViewHolderRatingTV.text = pet.popularityRating.toString()
        binding.petsViewHolderImageIV.load(pet.titleImg)

        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK) {
            binding.petsViewHolderImageIV.transitionName =
                getContext().getString(R.string.transition_image, pet.uid)
            binding.petsViewHolderNameTV.transitionName =
                getContext().getString(R.string.transition_name, pet.uid)
        }
    }
}