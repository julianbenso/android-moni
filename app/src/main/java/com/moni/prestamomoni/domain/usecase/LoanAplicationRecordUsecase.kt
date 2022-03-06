package com.moni.prestamomoni.domain.usecase

import com.moni.prestamomoni.api.LoanAplicationListApiClient
import com.moni.prestamomoni.data.RegistrationResult
import com.moni.prestamomoni.data.RequestResult
import com.moni.prestamomoni.domain.model.Loan
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.POST

interface LoanAplicationRecordUsecase {
    fun call(loan : Loan) : Single<RegistrationResult>
}

class LoanAplicationRecordUsecaseImpl() : LoanAplicationRecordUsecase{
    val apiInterface = LoanAplicationListApiClient().getStatusLoan().create(LoanAplicationRecordApiInterface::class.java)

    val cache : Single<RegistrationResult> = apiInterface.setLoanAplicationRecord()
        .subscribeOn(Schedulers.io())
        .cache()

    override fun call(loan: Loan): Single<RegistrationResult> {
        return cache
    }

    interface LoanAplicationRecordApiInterface{
        @POST()//hacer el curl el cual cual nose como se escribe
        fun setLoanAplicationRecord() : Single<RegistrationResult>
    }

}