package ru.jelezov.stockstracker.data.locale.room

import kotlinx.coroutines.flow.Flow
import ru.jelezov.stockstracker.data.locale.LocalDataSource
import ru.jelezov.stockstracker.model.StocksData
import javax.inject.Inject


class RoomDataSource @Inject constructor(private val db: StocksDataBase): LocalDataSource {

    override suspend fun loadFavouriteStocks(): List<StocksData> {
        return db.stocksDao().readAllFavouriteList()

    }

    override suspend fun loadStocks(): List<StocksData> {
        return db.stocksDao().readAllStocks()
    }

    override fun insertStocks(stocksFromNetwork: List<StocksData>) {
        val stocks = stocksFromNetwork.map { stocks ->
            StocksData(
                id = stocks.id,
                nameCompany = stocks.nameCompany,
                fullNameCompany = stocks.fullNameCompany,
                currentPrice = stocks.currentPrice,
                dayDelta = stocks.dayDelta,
                percentDayDelta = stocks.percentDayDelta,
                isFavourite = false
            )
        }
        db.stocksDao().insertStocks(stocks)
    }

    override suspend fun updateStocks(stocks: StocksData)  {
        db.stocksDao().update(stocks)
    }

    override suspend fun searchStocks(query: String): List<StocksData>  {
       return db.stocksDao().searchStocks(query)
    }


}