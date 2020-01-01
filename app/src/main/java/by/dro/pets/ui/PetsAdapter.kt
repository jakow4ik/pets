package by.dro.pets.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.data.Pet

class PetsAdapter(private var pets: MutableList<Pet>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateData(pets: MutableList<Pet>?){
        this.pets = pets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PetViewHolder(parent)
    }

    override fun getItemCount(): Int = pets?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is PetViewHolder) holder.bind(pets?.get(position))
    }


}