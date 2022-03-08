package com.moni.prestamomoni.data

import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.LoanStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

interface LoanRepository {
    fun loan(dni :Dni) : Single<LoanStatus>
}

class LoanRepositoryImpl(private val loanRemoteSource : LoanRemoteSource) : LoanRepository{

    override fun loan(dni: Dni): Single<LoanStatus> {
        return loanRemoteSource.loan(dni).subscribeOn(Schedulers.io())
    }
}