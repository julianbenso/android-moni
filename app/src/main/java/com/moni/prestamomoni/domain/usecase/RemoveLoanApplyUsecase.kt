package com.moni.prestamomoni.domain.usecase

import com.moni.prestamomoni.data.api.LoanAplicationListApiInterface
import com.moni.prestamomoni.domain.model.Dni
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

interface RemoveLoanApplyUsecase{
    fun call(dni : Dni) : Single<String>
}

class RemoveLoanApplyUsecaseImpl (loanAplicationListApiInterface : LoanAplicationListApiInterface) : RemoveLoanApplyUsecase {
    private val apiInterface = loanAplicationListApiInterface
    override fun call(dni: Dni) : Single<String>  {
        return apiInterface.removeLoanAplication(dni.digits).subscribeOn(Schedulers.io()).map {
            when (it) {
                "null","not found" -> "ERROR"
                else -> "OK"
            }
        }

    }
}