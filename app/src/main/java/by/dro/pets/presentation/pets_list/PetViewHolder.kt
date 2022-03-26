package by.dro.pets.presentation.pets_list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.Config
import by.dro.pets.R
import by.dro.pets.databinding.PetViewHolderBinding
import by.dro.pets.domain.entities.Dog
import by.dro.pets.util.getContext
import by.dro.pets.util.load

class PetViewHolder(parent: ViewGroup, selectedListener: PetsAdapter.PetSelectedListener?):
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
    R.layout.pet_view_holder, parent, false)) {

    private val binding = PetViewHolderBinding.bind(itemView)
    init {
        itemView.setOnClickListener {
            selectedListener?.onPetSelected(pet, binding.imageView, binding.name)
        }
    }

    private var pet: Dog? = null

    fun bind(pet: Dog?) {
        this.pet = pet

        binding.name.text = pet?.name

        binding.imageView.load(pet?.titleImg ?: "")


        if (Build.VERSION.SDK_INT >= Config.MIN_TRANSITION_SDK){
            binding.imageView.transitionName = String.format(getContext().getString(R.string.transition_image, pet?.uid))
            binding.name.transitionName = String.format(getContext().getString(R.string.transition_name, pet?.uid))
        }
    }
}