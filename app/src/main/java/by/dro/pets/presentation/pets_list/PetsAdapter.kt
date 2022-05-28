package by.dro.pets.presentation.pets_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.dro.pets.databinding.PetViewHolderBinding
import by.dro.pets.domain.entities.Dog

class PetsAdapter(private val selectedListener: PetSelectedListener?) :
    ListAdapter<Dog, PetViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
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

    interface PetSelectedListener {
        fun onPetSelected(pet: Dog?, imageView: ImageView, textView: TextView)
        fun onBookmarkClicked(pet: Dog?)
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Dog>() {
    override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem == newItem
    }
}