package com.moni.prestamomoni.domain.usecase

import com.moni.prestamomoni.data.api.LoanAplicationListApiInterface
import com.moni.prestamomoni.data.dto.RegistrationResult
import com.moni.prestamomoni.domain.model.Loan
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.POST

interface SaveLoanAplicationUsecase {
    fun call(loan: Loan): Single<RegistrationResult>
}

class SaveLoanAplicationUsecaseImpl(
    private val loanAplicationListApiInterface: LoanAplicationListApiInterface) : SaveLoanAplicationUsecase {

    override fun call(loan: Loan): Single<RegistrationResult> {
        return loanAplicationListApiInterface
            .setLoanAplicationRecord(loan)
            .map { if(it.error.isNullOrEmpty()) RegistrationResult.OK else RegistrationResult.FAIL }
            .subscribeOn(Schedulers.io())
    }
}