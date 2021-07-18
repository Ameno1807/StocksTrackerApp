package ru.jelezov.stockstracker.data.locale.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.jelezov.stockstracker.model.StocksData

@Database(
    entities = [StocksData::class],
    version = 1,
    exportSchema = false)

abstract class StocksDataBase : RoomDatabase() {
    abstract fun stocksDao(): StocksDao
}