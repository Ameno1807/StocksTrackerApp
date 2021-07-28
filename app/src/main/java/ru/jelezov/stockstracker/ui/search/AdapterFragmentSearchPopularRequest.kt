package ru.jelezov.stockstracker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.ItemSuggestionsCompanyBinding
import ru.jelezov.stockstracker.model.CompanyBrief

class AdapterFragmentSearchPopularRequest(private val onPopularClickListener: (CharSequence) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<CompanyBrief, AdapterFragmentSearchPopularRequest.ViewHolder>(DiffCallback()) {

   inner class ViewHolder(binding: ItemSuggestionsCompanyBinding) : RecyclerView.ViewHolder(binding.root) {

        private val fullCompanyName: TextView = itemView.findViewById(R.id.company_name)


        fun bind(item: CompanyBrief) {
            fullCompanyName.text = item.ticker
        }
        init {
            binding.companyName.apply {
                setOnClickListener { onPopularClickListener(text) }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemSuggestionsCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<CompanyBrief>() {
        override fun areItemsTheSame(oldItem: CompanyBrief, newItem: CompanyBrief): Boolean {
           return oldItem.ticker == newItem.ticker
        }

        override fun areContentsTheSame(oldItem: CompanyBrief, newItem: CompanyBrief): Boolean {
           return oldItem == newItem
        }
    }




}