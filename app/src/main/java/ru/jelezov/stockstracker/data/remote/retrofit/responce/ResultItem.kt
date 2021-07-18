package ru.jelezov.stockstracker.data.remote.retrofit.responce


data class ResultItem(
    val id: Int,
    val regularMarketChangePercent: Double,
    val regularMarketChange: Float,
    val regularMarketPrice: Double,
    val longName: String,
    val symbol: String,
)