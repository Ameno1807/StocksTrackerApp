package ru.jelezov.stockstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks_table")
data class StocksData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nameCompany: String,
    val fullNameCompany: String,
    val currentPrice: String,
    val dayDelta: String,
    val percentDayDelta: String,
    val isFavourite: Boolean = false
)
