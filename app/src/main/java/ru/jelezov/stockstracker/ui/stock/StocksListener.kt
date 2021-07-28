package ru.jelezov.stockstracker.ui.stock

import ru.jelezov.stockstracker.model.StocksData

interface StocksListener {
    fun click(stocksData: StocksData)
}