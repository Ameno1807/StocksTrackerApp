package ru.jelezov.stockstracker.ui.stock

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.jelezov.stockstracker.R
import ru.jelezov.stockstracker.databinding.ViewItemStockBinding
import ru.jelezov.stockstracker.model.StocksData
import ru.jelezov.stockstracker.utils.StocksDiffCallback
import java.text.DecimalFormat


open class AdapterFragmentStocksList(
   private val listener: StocksListener
) : RecyclerView.Adapter<AdapterFragmentStocksList.ViewHolder>() {

    var oldStocksData: List<StocksData> = ArrayList()
    set(value) {
        val diffUtil = StocksDiffCallback(field, value)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        field = value
        diffResult.dispatchUpdatesTo(this@AdapterFragmentStocksList)
    }
    var newStocksData: MutableList<StocksData> = ArrayList()

    override fun getItemCount(): Int {
        return oldStocksData.size
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

            setFavouriteImageResource(binding.liked, item.isFavourite)

            binding.liked.setOnClickListener {
                listener.click(item)
                setFavouriteImageResource(binding.liked, item.isFavourite)
                val index = oldStocksData.indexOfFirst { it.id == item.id }
                val updateStocks = oldStocksData[index].copy(isFavourite = !item.isFavourite)
                newStocksData = oldStocksData.toMutableList()
                newStocksData[index] = updateStocks
                Log.e("Tag", "newStocksData -> $newStocksData")
                Log.e("Tag", "oldStocksData -> $oldStocksData")
                oldStocksData = newStocksData
                addStocks(oldStocksData)
                notifyDataSetChanged()
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
        val item = oldStocksData[position]
        holder.bind(item)
    }

 fun addStocks(stocks: List<StocksData>) {
        this.oldStocksData = stocks
        Log.e("Tag", "List -> $stocks")
        notifyDataSetChanged()
    }

}