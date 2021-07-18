package ru.jelezov.stockstracker.repository

import android.util.Log
import ru.jelezov.stockstracker.data.locale.room.RoomDataSource
import ru.jelezov.stockstracker.data.remote.retrofit.RetrofitDataSource
import ru.jelezov.stockstracker.model.StocksData
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: RoomDataSource,
    private val remoteDataSource: RetrofitDataSource
) {
    suspend fun loadStocks(): List<StocksData> {
        return try {
            val stocksDB = localDataSource.loadStocks()
            if (stocksDB.isEmpty()) {
                val stocksFromNetwork = remoteDataSource.loadStocks()
                localDataSource.insertStocks(stocksFromNetwork)
                stocksFromNetwork
            } else stocksDB
        } finally {
            Log.e("Tag", "Error -> Repository(loadStocks)")
        }
    }

    suspend fun loadFavouritesList(): List<StocksData> {
        return try {
            val stocksDB = localDataSource.loadFavouriteStocks()
            stocksDB
        } finally {
            Log.e("Tag", "Error -> Repository(loadFavouritesList)")
        }
    }

   suspend fun updateFavorite(stock: StocksData) {
       return localDataSource.updateStocks(stock)
   }


}