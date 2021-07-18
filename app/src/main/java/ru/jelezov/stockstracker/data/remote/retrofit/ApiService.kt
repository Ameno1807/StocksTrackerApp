package ru.jelezov.stockstracker.data.remote.retrofit

import retrofit2.http.GET
import ru.jelezov.stockstracker.data.remote.retrofit.responce.GlobalQuote

interface ApiService {
    @GET("/market/v2/get-quotes?region=US")
    suspend fun stocksLoad(): GlobalQuote
}