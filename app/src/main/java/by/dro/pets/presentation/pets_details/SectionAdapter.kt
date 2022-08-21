package by.dro.pets.presentation.pets_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.dro.pets.databinding.DetailSectionItemBinding
import by.dro.pets.databinding.DetailSectionViewHolderBinding
import by.dro.pets.domain.entities.SectionItem
import by.dro.pets.util.getContext

class SectionAdapter :
    ListAdapter<PetsDetailViewModel.DescriptionSection, SectionViewHolder>(SectionDiff) {

    private var expandedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = DetailSectionViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(getItem(position), expandedPosition == position)
        holder.binding.detailSectionTitleTV.setOnClickListener {
            val lastExpandedPosition = expandedPosition
            expandedPosition = if (expandedPosition == position) null else position
            lastExpandedPosition?.let { notifyItemChanged(it) }
            notifyItemChanged(position)
        }
    }
}

class SectionViewHolder(
    val binding: DetailSectionViewHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private val itemBindings: MutableList<DetailSectionItemBinding> = mutableListOf()
    private var sectionData: PetsDetailViewModel.DescriptionSection? = null

    fun bind(section: PetsDetailViewModel.DescriptionSection, isExpanded: Boolean) {
        sectionData = section
        updateSectionItems(section.items)
        binding.detailSectionTitleTV.text = section.title
        binding.detailSectionItemsLL.isVisible = isExpanded
        binding.detailSectionTitleTV.isSelected = isExpanded
    }

    private fun inflateItemBinding(): DetailSectionItemBinding {
        val itemBinding = DetailSectionItemBinding.inflate(LayoutInflater.from(getContext()))
        itemBindings.add(itemBinding)
        return itemBinding
    }

    private fun updateSectionItems(items: List<SectionItem>) {
        binding.detailSectionItemsLL.removeAllViews()
        items.forEachIndexed { index, sectionItem ->
            val itemBinding = itemBindings.getOrNull(index) ?: inflateItemBinding()
            itemBinding.sectionItemTitleTV.text = sectionItem.title
            itemBinding.sectionItemBodyTV.text = sectionItem.body
            binding.detailSectionItemsLL.addView(itemBinding.root)
        }
    }
}

object SectionDiff : DiffUtil.ItemCallback<PetsDetailViewModel.DescriptionSection>() {
    override fun areItemsTheSame(
        oldItem: PetsDetailViewModel.DescriptionSection,
        newItem: PetsDetailViewModel.DescriptionSection
    ) = oldItem.title == newItem.title

    override fun areContentsTheSame(
        oldItem: PetsDetailViewModel.DescriptionSection,
        newItem: PetsDetailViewModel.DescriptionSection
    ) = oldItem == newItem
}