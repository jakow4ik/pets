package by.dro.pets.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.R
import by.dro.pets.data.Pet
import kotlinx.android.synthetic.main.pet_view_holder.view.*

class PetViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
    R.layout.pet_view_holder, parent, false)) {

    fun bind(pet: Pet?){
        itemView.uid.text = pet?.uid
    }
}