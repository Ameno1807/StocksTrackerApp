package ru.jelezov.stockstracker.data.remote.retrofit

import ru.jelezov.stockstracker.data.remote.RemoteDataSource
import ru.jelezov.stockstracker.model.StocksData
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val service: ApiService) : RemoteDataSource {
   override suspend fun loadStocks() : List<StocksData> {
        return service.stocksLoad().quoteResponse.result.map { stockResponse ->
            StocksData(
                id = stockResponse.id,
                nameCompany = stockResponse.symbol,
                fullNameCompany = stockResponse.longName,
                currentPrice = stockResponse.regularMarketPrice.toString(),
                dayDelta = stockResponse.regularMarketChange.toString(),
                percentDayDelta = stockResponse.regularMarketChangePercent.toString(),
                isFavourite = false
            )
        }
    }
}