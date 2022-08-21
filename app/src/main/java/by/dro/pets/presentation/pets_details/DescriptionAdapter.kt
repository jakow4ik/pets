package by.dro.pets.presentation.pets_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.R
import by.dro.pets.databinding.DetailDescriptionBinding
import by.dro.pets.util.EMPTY
import by.dro.pets.util.getContext

private const val ITEM_POSITION = 0

class DescriptionAdapter : RecyclerView.Adapter<DescriptionViewHolder>() {

    private val items = mutableListOf<DescriptionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val binding = DetailDescriptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return DescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
        holder.bind(items[ITEM_POSITION])
    }

    override fun getItemCount() = items.size

    fun setDescription(data: DescriptionData?) {
        if (data == null) {
            items.clear()
            notifyItemRemoved(ITEM_POSITION)
        } else {
            items.add(data)
            notifyItemInserted(ITEM_POSITION)
        }
    }
}

class DescriptionViewHolder(
    private val binding: DetailDescriptionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: DescriptionData) {
        binding.detailDescriptionTV.text = data.descriptionText
        binding.detailPopularityTV.text =
            getContext().getString(R.string.popularity_rating_n, data.popularityRating.toString())
    }
}

class DescriptionData(
    val descriptionText: String = String.EMPTY,
    val popularityRating: Double = 0.0,
)