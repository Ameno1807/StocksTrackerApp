package ru.jelezov.stockstracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.jelezov.stockstracker.data.locale.room.RoomDataSource
import ru.jelezov.stockstracker.data.locale.room.StocksDataBase
import ru.jelezov.stockstracker.data.remote.retrofit.ApiKeyInterceptor
import ru.jelezov.stockstracker.data.remote.retrofit.ApiService
import ru.jelezov.stockstracker.data.remote.retrofit.RetrofitDataSource
import java.util.concurrent.TimeUnit.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provide(apiService: ApiService) : RetrofitDataSource {
        return RetrofitDataSource(apiService)
    }

    @Provides
    fun provideRoomDataSource(stocksDataBase: StocksDataBase) : RoomDataSource {
        return RoomDataSource(stocksDataBase)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val build = OkHttpClient.Builder()
            .readTimeout(30, SECONDS)
            .connectTimeout(39, SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(ApiKeyInterceptor)
            .build()
        return build
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://apidojo-yahoo-finance-v1.p.rapidapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        StocksDataBase::class.java,
        "stocks_database"
    ).build()

    @Provides
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}