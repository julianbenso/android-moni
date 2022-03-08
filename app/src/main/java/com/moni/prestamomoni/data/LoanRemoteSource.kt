package com.moni.prestamomoni.data

import com.moni.prestamomoni.data.api.LoanApiInterface
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.model.LoanStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface LoanRemoteSource {
    fun loan(dni: Dni): Single<LoanStatus>
}

class LoanRemoteSourceImpl(private val loanApiInterface: LoanApiInterface) : LoanRemoteSource {
    private val header = "ZGpzOTAzaWZuc2Zpb25kZnNubm5u"

    override fun loan(dni: Dni): Single<LoanStatus> {
        return loanApiInterface.loanStatus(dni.digits, header)
            .map {
                return@map when {
                    it.isError == true -> LoanStatus.ERROR
                    it.status == "approve" -> LoanStatus.APROVED
                    else -> LoanStatus.REJECTED
                }
            }.subscribeOn(Schedulers.io())
    }
}
