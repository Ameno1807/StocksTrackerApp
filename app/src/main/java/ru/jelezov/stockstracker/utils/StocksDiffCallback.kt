package ru.jelezov.stockstracker.utils

import androidx.recyclerview.widget.DiffUtil
import ru.jelezov.stockstracker.model.StocksData

class StocksDiffCallback(
    private val oldList: List<StocksData>,
    private val newList: List<StocksData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldStocks = oldList[oldItemPosition]
        val newStocks = newList[newItemPosition]
        return oldStocks.id == newStocks.id && oldStocks.isFavourite == newStocks.isFavourite
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldStocks = oldList[oldItemPosition]
        val newStocks = newList[newItemPosition]
        return oldStocks == newStocks
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}