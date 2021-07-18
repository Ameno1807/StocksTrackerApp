package ru.jelezov.stockstracker.data.remote.retrofit

import okhttp3.Interceptor

object ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val origin = chain.request()
        val urlBuilder = origin.url.newBuilder()
        val url = urlBuilder
            .addQueryParameter("rapidapi-key", "8613caacc5msh78c5897758f6539p161b2cjsncae9c21938a7")
            .addQueryParameter("symbols", "AMD,IBM,AAPL,GOOGL,AMZN,BAC,MSFT,TSLA,MA,BAC,INTC,YNDX")
            .build()

        val requestBuilder = origin.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}