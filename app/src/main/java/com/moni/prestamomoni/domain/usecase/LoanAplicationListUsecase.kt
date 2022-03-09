package com.moni.prestamomoni.domain.usecase

import com.google.gson.Gson
import com.moni.prestamomoni.data.api.LoanAplicationListApiInterface
import com.moni.prestamomoni.data.dto.RequestLoanListResponse
import com.moni.prestamomoni.domain.model.Loan
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

interface LoanAplicationListUsecase {
    fun call(): Single<List<Loan>>
}

class LoanAplicationListUsecaseImpl(loanAplicationListApiInterface: LoanAplicationListApiInterface) :
    LoanAplicationListUsecase {
    private val request: Single<List<RequestLoanListResponse>> =
        loanAplicationListApiInterface.getLoanAplicationList() // Single<Map<String, Any>>
            .flatMapObservable { Observable.fromIterable(it.values) } // Observable<Any> me quedo con los valores
            .map {
                val jsonTree = Gson().toJsonTree(it) // Transformo un any en un JsonTree
                Gson().fromJson(jsonTree, RequestLoanListResponse::class.java)
            } // Observable<RequestLoanListResponse>
            .toList() // Single<List<RequestLoanListResponse>>
            .subscribeOn(Schedulers.io())

    override fun call(): Single<List<Loan>> {
        return request //Single<List<RequestLoanListResponse>>
            .flatMapObservable { Observable.fromIterable(it) } //Observable<RequestLoanListResponse>
            .map {
                return@map Loan(
                    it.nombre,
                    it.apellido,
                    it.dni,
                    it.email,
                    it.genero,
                    it.estadoPrestamo
                )//Observable<Loan>
            }
            .toList()//Single<List<Loan>>
    }
}

class FakeLoanAplicationListUsecaseImpl() : LoanAplicationListUsecase {
    override fun call(): Single<List<Loan>> {
        val prestamo1 = Loan("jose", "perez", "40506247", "jose@gmail.com", "Masculino", "aproved")
        val prestamo2 =
            Loan("juan", "gutierrez", "16654403", "juan@gmail.com", "Masculino", "rejected")
        val prestamo3 =
            Loan("julian", "Benso", "16545979", "julian@gmail.com", "Masculino", "aproved")
        val fakeLoanList = listOf<Loan>(prestamo1, prestamo2, prestamo3)

        return Single.just(fakeLoanList)
            .delay(2000, TimeUnit.MILLISECONDS)
    }

}
