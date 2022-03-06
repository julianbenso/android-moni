package com.moni.prestamomoni.data

import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.LoanStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy

interface LoanRepository {
    fun loan(dni :Dni) : Single<LoanStatus>
}

class LoanRepositoryImpl(private val loanRemoteSource : LoanRemoteSource) : LoanRepository{

    override fun loan(dni: Dni): Single<LoanStatus> {
        val requestResponse : Single<String> = loanRemoteSource.loan(dni)
        //Esto debe estar mal, lo hice asi pq pense que el repository era el que debia transformar los datos
        return when{
            requestResponse == Single.just("error") -> Single.just(LoanStatus.ERROR)
            requestResponse == Single.just("aproved") -> Single.just(LoanStatus.APROVED)
            requestResponse == Single.just("rejected") -> Single.just(LoanStatus.REJECTED)
            else -> Single.just(LoanStatus.ERROR)
        }


    }

}