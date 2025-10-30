package com.example.expensetracker.data.remote

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}