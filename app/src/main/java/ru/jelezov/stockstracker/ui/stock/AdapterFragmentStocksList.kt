package ru.jelezov.stockstracker.ui.stock

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.ViewItemStockBinding
import ru.jelezov.stockstracker.model.StocksData
import java.text.DecimalFormat


open class AdapterFragmentStocksList(
   private val onStarClick: (StocksData) -> Unit
) : ListAdapter<StocksData, AdapterFragmentStocksList.ViewHolder>(DiffCallback()) {


    inner class ViewHolder(private val binding: ViewItemStockBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StocksData, onStarClick: (StocksData) -> Unit) {
            binding.companyName.text = item.nameCompany
            binding.fullCompanyName.text = item.fullNameCompany
            binding.currentPrice.text = "$${item.currentPrice}"
            binding.dayDelta.text =
                (DecimalFormat("##.##").format(item.dayDelta.toDouble()) + " " + "(" + DecimalFormat(
                    "##.##"
                ).format(item.percentDayDelta.toDouble()) + "%" + ")")

            if (item.dayDelta < 0.toString()){
                binding.dayDelta.setTextColor(Color.RED)
            }

            if ((adapterPosition % 2) != 0) {
                binding.view.setBackgroundColor(Color.WHITE)
            }

            setFavouriteImageResource(binding.liked, item.isFavourite)

            binding.liked.setOnClickListener {
                item.isFavourite = !item.isFavourite
                onStarClick.invoke(item)
                setFavouriteImageResource(binding.liked, item.isFavourite)
            }
        }
    }

    private fun setFavouriteImageResource(favouriteImage: ImageView, favourite: Boolean) {
        val imageResource =
            if (favourite) R.drawable.star_add else R.drawable.star
        favouriteImage.setImageResource(imageResource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ViewItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onStarClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<StocksData>() {
        override fun areItemsTheSame(oldItem: StocksData, newItem: StocksData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StocksData, newItem: StocksData): Boolean {
            return oldItem == newItem
        }
    }

}