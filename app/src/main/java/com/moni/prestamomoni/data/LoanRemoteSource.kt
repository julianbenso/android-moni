package com.moni.prestamomoni.data

import com.moni.prestamomoni.api.CanApplyForLoanApiClient
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.LoanStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LoanRemoteSource {
    fun loan(dni : Dni) : Single<String>

}

class LoanRemoteSourceImpl(/*private val loanApiInterface : LoanApiInterface*/) : LoanRemoteSource{
    private val header = "ZGpzOTAzaWZuc2Zpb25kZnNubm5u"
    private val apiInterface : LoanApiInterface = CanApplyForLoanApiClient().getStatusLoan().create(LoanApiInterface::class.java)
    //falta la inyeccion de dependencias de esto
    override fun loan(dni: Dni): Single<String> {

        val serverResponse = apiInterface.loanStatus(dni,header).execute().body()

        //podria haber hecho que devuelva eun LoanStatus, pero queria que el repository hiciera algo
        return when{
            serverResponse?.isError == true -> Single.just("error")
            serverResponse?.status == "approve" -> Single.just("aproved")
            else -> Single.just("rejected")
        }.subscribeOn(Schedulers.io())

    }
}

interface LoanApiInterface {
    //https://api.moni.com.ar/api/v4/scoring/pre-score/40506247
    @GET("{dni}")
    fun loanStatus(@Path("dni") dni : Dni, @Header("credential") header :String) : Call<RequestResult>
    }

