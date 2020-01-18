package by.dro.pets.ui

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.data.Pet

class PetsAdapter(private val selectedListener: PetSelectedListener?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var petsList: List<Pet>? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PetViewHolder(parent, selectedListener)
    }

    override fun getItemCount(): Int = petsList?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is PetViewHolder) holder.bind(petsList?.get(position))
    }


    interface PetSelectedListener{
        fun onPetSelected(pet: Pet?, imageView: ImageView, textView: TextView)
    }
}