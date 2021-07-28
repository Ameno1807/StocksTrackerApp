package ru.jelezov.stockstracker.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import ru.jelezov.stockstracker.data.locale.room.RoomDataSource
import ru.jelezov.stockstracker.data.remote.retrofit.RetrofitDataSource
import ru.jelezov.stockstracker.model.StocksData
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

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

    suspend fun searchStocks(query: String): List<StocksData> {
        return try {
            val stocksDB = localDataSource.searchStocks(query)
            stocksDB
        } finally {
            Log.e("Tag", "Error -> Repository(loadFavouritesList)")
        }
    }


}