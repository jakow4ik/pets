package by.dro.pets.presentation.pets_list

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.R
import by.dro.pets.databinding.PetViewHolderBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.pets_list.PetsAdapter.Companion.ARG_BOOKMARK
import by.dro.pets.util.getContext
import by.dro.pets.util.load


class PetViewHolder(
    private val binding: PetViewHolderBinding,
    selectedListener: PetsAdapter.PetClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            selectedListener.onPetClicked(
                pet ?: return@setOnClickListener,
                binding.petsViewHolderImageIV,
                binding.petsViewHolderNameTV,
                binding.petsViewHolderBookmark,
            )
        }
        binding.petsViewHolderBookmark.setOnClickListener {
            selectedListener.onBookmarkClicked(pet ?: return@setOnClickListener)
        }
    }

    private var pet: Dog? = null

    fun bind(pet: Dog) {
        this.pet = pet

        binding.petsViewHolderNameTV.text = pet.name
        binding.petsViewHolderTypeTV.text = pet.country
        binding.petsViewHolderRatingTV.text = pet.popularityRating.toString()
        binding.petsViewHolderImageIV.load(pet.titleImg)
        bindBookmark(pet.isBookmarked)

        binding.petsViewHolderImageIV.transitionName =
            getContext().getString(R.string.transition_image, pet.uid)
        binding.petsViewHolderNameTV.transitionName =
            getContext().getString(R.string.transition_name, pet.uid)
        binding.petsViewHolderBookmark.transitionName =
            getContext().getString(R.string.transition_bookmark, pet.uid)
    }

    fun bind(pet: Dog, payloads: Bundle) {
        this.pet = pet

        if (payloads.containsKey(ARG_BOOKMARK)) {
            bindBookmark(payloads.getBoolean(ARG_BOOKMARK, false))
        }
    }

    private fun bindBookmark(isBookmarked: Boolean) {
        binding.petsViewHolderBookmark.setImageResource(
            if (isBookmarked) R.drawable.ic_bookmark_enable else R.drawable.ic_bookmark_disable
        )
    }
}