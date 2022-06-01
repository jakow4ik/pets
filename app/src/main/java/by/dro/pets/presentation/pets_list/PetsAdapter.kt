package by.dro.pets.presentation.pets_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.dro.pets.databinding.PetViewHolderBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.presentation.pets_list.PetsAdapter.Companion.ARG_BOOKMARK


class PetsAdapter(private val selectedListener: PetClickListener) :
    ListAdapter<Dog, PetViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: PetViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.bind(getItem(position), payloads.first() as Bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder(
            PetViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            selectedListener
        )
    }

    interface PetClickListener {
        fun onPetClicked(pet: Dog, imageView: ImageView, textView: TextView, bookmark: ImageView)
        fun onBookmarkClicked(pet: Dog)
    }

    companion object {
        const val ARG_BOOKMARK = "arg_bookmark"
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Dog>() {
    override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Dog, newItem: Dog): Any? {
        return if (oldItem.isBookmarked == newItem.isBookmarked)
            null else bundleOf(ARG_BOOKMARK to newItem.isBookmarked)
    }
}