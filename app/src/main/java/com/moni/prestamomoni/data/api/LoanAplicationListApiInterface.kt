package com.moni.prestamomoni.data.api

import com.moni.prestamomoni.data.dto.ErrorDTO
import com.moni.prestamomoni.data.dto.RegistrationResult
import com.moni.prestamomoni.data.dto.RequestLoanListResponse
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.Loan
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface LoanAplicationListApiInterface {
    @GET("https://wired-torus-98413.firebaseio.com/users.json")
    fun getLoanAplicationList(): Single<Map<String,Any>>

    @POST("https://wired-torus-98413.firebaseio.com/users.json")
    fun setLoanAplicationRecord(@Body loanAplicationRequest : Loan): Single<ErrorDTO>

    @DELETE("https://wired-torus-98413.firebaseio.com/users.json/{dni}.json")
    fun removeLoanAplication(@Path("dni")dni: String) : Single<String>
}