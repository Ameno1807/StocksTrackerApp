package ru.jelezov.stockstracker.data.locale.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.jelezov.stockstracker.model.StocksData


@Dao
interface StocksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStocks(stocks: List<StocksData>)

    @Query("SELECT * FROM stocks_table ORDER BY id ASC")
    fun readAllStocks(): List<StocksData>

    @Update
    suspend fun update(stock: StocksData)

    @Query("select * from stocks_table where nameCompany like :query or fullNameCompany like :query")
    fun searchStocks(query: String): List<StocksData>

    @Query("SELECT * FROM stocks_table WHERE isFavourite = 1 ORDER BY id ASC")
    fun readAllFavouriteList(): Flow<List<StocksData>>

}