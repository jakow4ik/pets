package by.dro.pets.ui

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.R
import by.dro.pets.data.Pet
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

        Glide.with(getContext())
            .load(pet?.titleImg)
            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
            .into(itemView.imageView)


        if (Build.VERSION.SDK_INT >= 21)
        itemView.imageView.transitionName = pet?.uid
    }

    private fun getContext(): Context{
        return itemView.context
    }
}