package ru.jelezov.stockstracker.ui.stock

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.ViewItemStockBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel
import java.text.DecimalFormat


open class AdapterFragmentStocksList(
   private val viewModel: FragmentStocksListViewModel
) : ListAdapter<StocksData, AdapterFragmentStocksList.ViewHolder>(DiffCallback()) {


    inner class ViewHolder(private val binding: ViewItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: StocksData) {
            binding.companyName.text = item.nameCompany
            binding.fullCompanyName.text = item.fullNameCompany
            binding.currentPrice.text = "$${item.currentPrice}"
            binding.dayDelta.text = (DecimalFormat("##.##").format(item.dayDelta.toDouble())+ " " + "("+ DecimalFormat("##.##").format(item.percentDayDelta.toDouble()) + "%" + ")")

            if (item.dayDelta < 0.toString()){
                binding.dayDelta.setTextColor(Color.RED)
            }
            updateFavorite(item.isFavourite)

        }
        private fun updateFavorite(isFavorite: Boolean) {
            val starIcon = if (isFavorite) R.drawable.star_add else R.drawable.star
            val starImageDrawable = ResourcesCompat
                .getDrawable(itemView.resources, starIcon, itemView.context.theme)
            binding.liked.setImageDrawable(starImageDrawable)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ViewItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ((position%2)==0) {
            val item = getItem(position)
            holder.bind(item)
        } else {
            holder.itemView.findViewById<View>(R.id.view).setBackgroundColor(Color.WHITE)
            val item = getItem(position)
            holder.bind(item)
        }

        holder.itemView.findViewById<ImageView>(R.id.liked).setOnClickListener {
            val item = getItem(position)
            if (item.isFavourite) {
                item.isFavourite = false
                holder.bind(item)
            } else {
                item.isFavourite = true
                holder.bind(item)
            }
            afterLikeClick(item)
        }
    }

    private fun afterLikeClick(currentItem: StocksData) {
        viewModel.updateFavorite(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<StocksData>() {
        override fun areItemsTheSame(oldItem: StocksData, newItem: StocksData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StocksData, newItem: StocksData): Boolean {
            return oldItem.id == newItem.id
        }
    }



}