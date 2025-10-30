package com.example.expensetracker.data.remote.ReklamApi

import retrofit2.http.GET
import retrofit2.http.Path


interface ReklamServiceApi{
    @GET("reklams")
    suspend fun getReklam(): List<Reklama>

    @GET("reklams/{id}")
    suspend fun getReklamById(
        @Path("id") id: String
    ): Reklama

}