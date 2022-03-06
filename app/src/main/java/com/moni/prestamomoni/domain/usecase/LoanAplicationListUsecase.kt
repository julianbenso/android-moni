package com.moni.prestamomoni.domain.usecase

import com.moni.prestamomoni.api.LoanAplicationListApiClient
import com.moni.prestamomoni.data.RequestLoanListResponse
import com.moni.prestamomoni.domain.model.Loan
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.create
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface LoanAplicationListUsecase {
    fun call() : Single<List<Loan>>
}

class LoanAplicationListUsecaseImpl() : LoanAplicationListUsecase{
     //creamos la apiInterface
    val apiInterface = LoanAplicationListApiClient().getStatusLoan().create(LoanAplicationListApiInterface::class.java)

    val cache : Single<List<RequestLoanListResponse>> = apiInterface.getLoanAplicationList()
        .subscribeOn(Schedulers.io())
        .cache()

    override fun call(): Single<List<Loan>> {
        return cache //Single<List<RequestLoanListResponse>>
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

    interface LoanAplicationListApiInterface{
        @GET
        fun getLoanAplicationList() : Single<List<RequestLoanListResponse>>
    }
}

class FakeLoanAplicationListUsecaseImpl() : LoanAplicationListUsecase{
    override fun call(): Single<List<Loan>> {
        val prestamo1 = Loan("jose", "perez", "40506247", "jose@gmail.com","Masculino","aproved")
        val prestamo2 = Loan("juan", "gutierrez", "16654403", "juan@gmail.com","Masculino","rejected")
        val prestamo3 = Loan("julian", "Benso", "16545979", "julian@gmail.com","Masculino","aproved")
        val fakeLoanList = listOf<Loan>(prestamo1,prestamo2,prestamo3)

        return Single.just(fakeLoanList)
            .delay(2000,TimeUnit.MILLISECONDS)
    }

}
