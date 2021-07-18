package ru.jelezov.stockstracker.data.locale.room

import androidx.room.*
import ru.jelezov.stockstracker.model.StocksData


@Dao
interface StocksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStocks(stocks: List<StocksData>)

    @Query("SELECT * FROM stocks_table WHERE isFavourite = 1 ORDER BY id ASC")
    fun readAllFavouriteList(): List<StocksData>

    @Query("SELECT * FROM stocks_table ORDER BY id ASC")
    fun readAllStocks(): List<StocksData>

    @Update
    suspend fun update(stock: StocksData)


}