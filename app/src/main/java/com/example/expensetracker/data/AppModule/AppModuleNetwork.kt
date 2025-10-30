package com.example.expensetracker.data.AppModule

import com.example.expensetracker.data.Repository.NewsRepository
import com.example.expensetracker.data.remote.ApiKeyInterceptor
import com.example.expensetracker.data.remote.NewsApiService
import com.example.expensetracker.data.remote.ReklamApi.ReklamServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleNetwork {

    private const val BASE_URL_NEWS = "https://newsapi.org/"
    private const val BASE_URL_REKLAM = "https://68f71a37f7fb897c66148640.mockapi.io/reklam/api/"
    private const val API_KEY = "7cb384f5ec434bab8fbd8d559c5466a1"

    // 🔹 API Key
    @Provides
    @Singleton
    fun provideApiKey(): String = API_KEY


    // 🔹 OkHttp Client (faqat News API uchun)
    @Provides
    @Singleton
    fun provideOkHttpClient(apiKey: String): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(logging)
            .build()
    }

    // 🔹 Retrofit — News API uchun
    @Provides
    @Singleton
    @Named("NewsRetrofit")
    fun provideNewsRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_NEWS)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🔹 Retrofit — Reklama API uchun
    @Provides
    @Singleton
    @Named("ReklamRetrofit")
    fun provideReklamRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_REKLAM)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🔹 News API Service
    @Provides
    @Singleton
    fun provideNewsApiService(@Named("NewsRetrofit") retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)

    // 🔹 Reklam API Service
    @Provides
    @Singleton
    fun provideReklamApiService(@Named("ReklamRetrofit") retrofit: Retrofit): ReklamServiceApi =
        retrofit.create(ReklamServiceApi::class.java)

    // 🔹 News Repository
    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApiService): NewsRepository =
        NewsRepository(api)
}
