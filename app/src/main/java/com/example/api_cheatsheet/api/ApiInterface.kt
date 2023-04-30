package com.example.api_cheatsheet.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET(value = "exchanges")
    suspend fun getExchangeList(
        @Query("per_page") perPage: Int = 250,
    ): Response<List<Exchanges>>

    @GET(value = "exchanges/{id}")
    suspend fun getExchangeDetail(
        @Path("id") id: String,
    ): Response<ExchangeDetail>
}