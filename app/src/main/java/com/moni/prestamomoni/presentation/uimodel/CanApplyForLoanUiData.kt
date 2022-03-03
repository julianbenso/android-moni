package com.moni.prestamomoni.presentation.uimodel

import com.moni.prestamomoni.core.Event
import com.moni.prestamomoni.domain.model.LoanStatus

data class CanApplyForLoanUiData(
    val showLoading : Event<Boolean>? = null,
    val showLoanStatus : Event<LoanStatus>? = null,
    val showError : Event<Throwable>? = null,
)
