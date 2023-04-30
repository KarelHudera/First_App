package com.example.api_cheatsheet.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val baseUrl = "https://api.coingecko.com/api/v3/"

object DataSource {
    private val ApiInterface by lazy {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(ApiInterface::class.java)
    }

    suspend fun getExchangesList(): Response<List<Exchanges>> {
        return ApiInterface.getExchangeList()
    }

    suspend fun getExchangesDetail(exchangeId: String): Response<ExchangeDetail> {
        return ApiInterface.getExchangeDetail(exchangeId)
    }
}