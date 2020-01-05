package by.dro.pets.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.Config
import by.dro.pets.R
import by.dro.pets.data.Pet
import by.dro.pets.util.load
import kotlinx.android.synthetic.main.pet_view_holder.view.*

class PetViewHolder(parent: ViewGroup, selectedListener: PetsAdapter.PetSelectedListener?):
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
    R.layout.pet_view_holder, parent, false)) {
    init {
        itemView.setOnClickListener {
            selectedListener?.onPetSelected(pet, itemView.imageView)
        }
    }

    private var pet: Pet? = null

    fun bind(pet: Pet?) {
        this.pet = pet

        itemView.uid.text = pet?.uid

        itemView.imageView.load(pet?.titleImg ?: "")


        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK)
        itemView.imageView.transitionName = pet?.uid
    }
}