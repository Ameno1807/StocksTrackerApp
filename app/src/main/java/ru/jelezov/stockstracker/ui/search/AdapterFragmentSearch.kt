package ru.jelezov.stockstracker.ui.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.ViewItemStockBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.ui.stock.StocksListener
import java.text.DecimalFormat

open class AdapterFragmentSearch(
    private val listener: StocksListener
) : RecyclerView.Adapter<AdapterFragmentSearch.ViewHolder>() {

    var stocksData = emptyList<StocksData>()
    var newStocksData = emptyList<StocksData>().toMutableList()

    override fun getItemCount(): Int {
        return stocksData.size
    }

    inner class ViewHolder(private val binding: ViewItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: StocksData) {
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
                  listener.click(item)
                  setFavouriteImageResource(binding.liked, item.isFavourite)
                  val index = stocksData.indexOfFirst { it.id == item.id }
                  val updateStocks = stocksData[index].copy(isFavourite = !item.isFavourite)
                  newStocksData = stocksData.toMutableList()
                  newStocksData[index] = updateStocks
                  stocksData = newStocksData
                  notifyDataSetChanged()
                  Log.e("Tag", "$newStocksData")
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
        val item = stocksData[position]
        holder.bind(item)
    }

    fun addStocks(stocks: List<StocksData>) {
        this.stocksData = stocks
        notifyDataSetChanged()
    }

}

