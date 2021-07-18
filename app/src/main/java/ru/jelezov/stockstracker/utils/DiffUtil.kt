package ru.jelezov.stockstracker.utils

import androidx.recyclerview.widget.DiffUtil
import ru.jelezov.stockstracker.model.StocksData

class MyDiffUtil(
    val oldList: List<StocksData>,
    val newList: List<StocksData>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].isFavourite == newList[newItemPosition].isFavourite
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].nameCompany != newList[newItemPosition].nameCompany -> {
                false
            }

            oldList[oldItemPosition].fullNameCompany != newList[newItemPosition].fullNameCompany -> {
                false
            }

            oldList[oldItemPosition].currentPrice != newList[newItemPosition].currentPrice -> {
                false
            }

            oldList[oldItemPosition].dayDelta != newList[newItemPosition].dayDelta -> {
                false
            }

            oldList[oldItemPosition].percentDayDelta != newList[newItemPosition].percentDayDelta -> {
                false
            }

            oldList[oldItemPosition].isFavourite != newList[newItemPosition].isFavourite -> {
                false
            }
            else -> true
        }
    }
}