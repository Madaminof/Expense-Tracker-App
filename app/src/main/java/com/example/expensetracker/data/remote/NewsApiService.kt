package com.example.expensetracker.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("v2/everything")
    suspend fun getFinanceNews(
        @Query("q") query: String = "finance OR economy OR bank OR uzbekistan",
        @Query("language") lang: String = "en",
        @Query("sortBy") sortBy: String = "publishedAt"
    ): NewsResponse
}