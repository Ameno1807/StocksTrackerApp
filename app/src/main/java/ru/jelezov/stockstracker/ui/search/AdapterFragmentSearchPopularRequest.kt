package ru.jelezov.stockstracker.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.model.CompanyBrief

class AdapterFragmentSearchPopularRequest(private val onClickCard: (item: CompanyBrief) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<CompanyBrief, AdapterFragmentSearchPopularRequest.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fullCompanyName: TextView = itemView.findViewById(R.id.company_name)


        fun bind(item: CompanyBrief) {
            fullCompanyName.text = item.ticker
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_suggestions_company, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val item = getItem(position)
            holder.bind(item)

    }

    class DiffCallback : DiffUtil.ItemCallback<CompanyBrief>() {
        override fun areItemsTheSame(oldItem: CompanyBrief, newItem: CompanyBrief): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: CompanyBrief, newItem: CompanyBrief): Boolean {
            TODO("Not yet implemented")
        }
    }




}