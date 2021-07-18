package ru.jelezov.stockstracker.data.remote

import ru.jelezov.stockstracker.model.StocksData

interface RemoteDataSource {
    suspend fun loadStocks(): List<StocksData>
}