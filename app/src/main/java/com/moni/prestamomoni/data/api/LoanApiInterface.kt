package com.moni.prestamomoni.data.api

import com.moni.prestamomoni.data.dto.RequestResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LoanApiInterface {
    @GET("{dni}")
    fun loanStatus(@Path("dni") dni : String, @Header("credential") header :String) : Single<RequestResult>
}