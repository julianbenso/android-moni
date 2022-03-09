package com.moni.prestamomoni.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moni.prestamomoni.core.Event
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.model.LoanStatus
import com.moni.prestamomoni.domain.usecase.CanApplyForLoanUsecase
import com.moni.prestamomoni.domain.usecase.SaveLoanAplicationUsecase
import com.moni.prestamomoni.presentation.uimodel.CanApplyForLoanUiData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlin.Exception

class CanApplyForLoanViewModel(
    private val canApplyForLoanUsecase: CanApplyForLoanUsecase,
    private val saveloanAplicationUsecase: SaveLoanAplicationUsecase
) : ViewModel() {

    private val composite = CompositeDisposable()

    private val _uiData = MutableLiveData<CanApplyForLoanUiData>()
    val uiData: LiveData<CanApplyForLoanUiData> = _uiData

    private var loanStatusResult : LoanStatus = LoanStatus.REJECTED

    fun canApplyForLoan(
        nombre: String,
        apellido: String,
        dni: String,
        email: String,
        genero: String,
    ) {
        val validatedDni: Dni = try {
            Dni(dni)
        } catch (e: Exception) {
            _uiData.value = CanApplyForLoanUiData(showError = Event(e))
            return
        }

        _uiData.value = CanApplyForLoanUiData(showLoading = Event(true))

        canApplyForLoanUsecase
            .call(validatedDni) // Single<LoanStatus>
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { loanStatus ->
                _uiData.value = CanApplyForLoanUiData(showLoanStatus = Event(loanStatus))
                this.loanStatusResult = loanStatus
            }
            .flatMap { saveloanAplicationUsecase.call(
                        Loan(
                            nombre,
                            apellido,
                            dni,
                            email,
                            genero,
                            loanStatusResult.toString()
                        )
                    )
            } // Single<RegistratioResult>
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                _uiData.value = CanApplyForLoanUiData(showLoading = Event(false))
            }
            .subscribeBy(
                onError = { _uiData.value = CanApplyForLoanUiData(showError = Event(it)) },
                onSuccess = {
                    _uiData.value = CanApplyForLoanUiData(showSaveLoanAplicationResult = Event(it))
                }
            )
            .addTo(composite)
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}