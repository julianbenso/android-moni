package com.moni.prestamomoni.domain.usecase

import com.moni.prestamomoni.data.LoanRepository
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.LoanStatus
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

interface CanApplyForLoanUsecase {
    fun call(dni: Dni): Single<LoanStatus>
}

class CanApplyForLoanUsecaseImpl (private val loanRepository: LoanRepository): CanApplyForLoanUsecase {
    override fun call(dni: Dni): Single<LoanStatus> {
        return loanRepository.loan(dni)
            .subscribeOn(Schedulers.io())
    }

}

class FakeCanApplyForLoanUsecaseImpl : CanApplyForLoanUsecase {
    override fun call(dni: Dni): Single<LoanStatus> {
        return Single
            .just(LoanStatus.APROVED)
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
    }

}