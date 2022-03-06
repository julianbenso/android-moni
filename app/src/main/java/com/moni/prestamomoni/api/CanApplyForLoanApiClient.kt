package com.moni.prestamomoni.api

import com.google.gson.GsonBuilder
import com.moni.prestamomoni.data.RequestResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CanApplyForLoanApiClient {

    private val baseUrl = "https://api.moni.com.ar/api/v4/scoring/pre-score/"

    fun getStatusLoan() : Retrofit{
        val builder = okhttp3.OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)

        val gson = GsonBuilder()
            .registerTypeAdapter(RequestResult::class.java,RequestResult())
            .create()

        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}