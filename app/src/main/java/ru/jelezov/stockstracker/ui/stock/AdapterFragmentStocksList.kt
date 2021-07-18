package ru.jelezov.stockstracker.ui.stock

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.ViewItemStockBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.stock.viewModel.FragmentStocksListViewModel
import ru.jelezov.stockstracker.utils.MyDiffUtil
import java.text.DecimalFormat


open class AdapterFragmentStocksList(
   private val viewModel: FragmentStocksListViewModel
) : RecyclerView.Adapter<AdapterFragmentStocksList.ViewHolder>() {

    private var oldStocksList = emptyList<StocksData>()

    inner class ViewHolder(private val binding: ViewItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
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
        fun updateFavorite(isFavorite: Boolean) {
            val starIcon = if (isFavorite) R.drawable.star_add else R.drawable.star
            val starImageDrawable = ResourcesCompat
                .getDrawable(itemView.resources, starIcon, itemView.context.theme)
            binding.liked.setImageDrawable(starImageDrawable)
        }
    }

    override fun getItemCount(): Int {
        return oldStocksList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ViewItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if ((position%2)==0) {
            val item = oldStocksList[position]
            holder.bind(item)
        } else {
            holder.itemView.findViewById<View>(R.id.view).setBackgroundColor(Color.WHITE)
            val item = oldStocksList[position]
            holder.bind(item)
        }

        holder.itemView.findViewById<ImageView>(R.id.liked).setOnClickListener { button ->
            val item = oldStocksList[position]
            if (item.isFavourite) {
                item.isFavourite = false
                //  button.setBackgroundResource(R.drawable.star)
                holder.bind(item)
            } else {
                item.isFavourite = true
                // button.setBackgroundResource(R.drawable.star_add)
                holder.bind(item)
            }
            afterLikeClick(item)

        }
    }

    private fun afterLikeClick(currentItem: StocksData) {
        viewModel.updateFavorite(currentItem)
    }

     fun setData(newList: List<StocksData>) {
        val diffUtil = MyDiffUtil(oldStocksList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        oldStocksList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}