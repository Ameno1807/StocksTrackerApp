package ru.jelezov.stockstracker.data.locale

import kotlinx.coroutines.flow.Flow
import ru.jelezov.stockstracker.model.StocksData


interface LocalDataSource {
    fun insertStocks(stocksFromNetwork: List<StocksData>)
    suspend fun loadStocks(): List<StocksData>
    suspend fun updateStocks(stocks: StocksData)
    suspend fun searchStocks(query: String): List<StocksData>
    fun readAllFavourite(): Flow<List<StocksData>>
}