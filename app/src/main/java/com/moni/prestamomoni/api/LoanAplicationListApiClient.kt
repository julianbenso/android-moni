package com.moni.prestamomoni.api

import com.google.gson.GsonBuilder
import com.moni.prestamomoni.data.RequestLoanListResponse
import com.moni.prestamomoni.data.RequestResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoanAplicationListApiClient {

    private val baseUrl = "https://wired-torus-98413.firebaseio.com/users.json"

    fun getStatusLoan() : Retrofit {
        val builder = okhttp3.OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)

        val gson = GsonBuilder()
            .registerTypeAdapter(RequestResult::class.java, RequestLoanListResponse())
            .create()

        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}