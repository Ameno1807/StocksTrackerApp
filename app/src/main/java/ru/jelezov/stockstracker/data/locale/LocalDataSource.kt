package ru.jelezov.stockstracker.data.locale

import kotlinx.coroutines.flow.Flow
import ru.jelezov.stockstracker.model.StocksData


interface LocalDataSource {
    suspend fun loadStocks(): List<StocksData>
    fun insertStocks(stocksFromNetwork: List<StocksData>)
    suspend fun loadFavouriteStocks(): List<StocksData>
    suspend fun updateStocks(stocks: StocksData)
    suspend fun searchStocks(query: String): List<StocksData>
}