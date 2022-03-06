package com.moni.prestamomoni.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moni.prestamomoni.core.Event
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.usecase.CanApplyForLoanUsecase
import com.moni.prestamomoni.domain.usecase.LoanAplicationRecordUsecase
import com.moni.prestamomoni.presentation.MainActivity
import com.moni.prestamomoni.presentation.uimodel.CanApplyForLoanUiData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlin.Exception

class CanApplyForLoanViewModel(
    private val canApplyForLoanUsecase: CanApplyForLoanUsecase,
    private val loanAplicationRecordUsecase : LoanAplicationRecordUsecase
) : ViewModel() {

    private val composite = CompositeDisposable()

    private val _uiData = MutableLiveData<CanApplyForLoanUiData>()
    val uiData: LiveData<CanApplyForLoanUiData> = _uiData
    lateinit var loanToRegister : Loan

    fun canApplyForLoan(dni: String) {
        val validatedDni: Dni = try {
            Dni(dni)
        } catch (e: Exception) {
            _uiData.value = CanApplyForLoanUiData(showError = Event(e))
            return
        }

        _uiData.value = CanApplyForLoanUiData(showLoading = Event(true))

        canApplyForLoanUsecase
            .call(validatedDni)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                _uiData.value = CanApplyForLoanUiData(showLoading = Event(false))
            }
            .subscribeBy(
                onError = { _uiData.value = CanApplyForLoanUiData(showError = Event(it)) },
                onSuccess = {
                    _uiData.value = CanApplyForLoanUiData(showLoanStatus = Event(it))

                    loanToRegister = MainActivity().loan//Esto debe estar mal
                    loanToRegister.estadoPresatamo = it.toString()
                    loanAplicationRecordUsecase.call(loanToRegister)
                }
            )
            .addTo(composite)
    }

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }
}