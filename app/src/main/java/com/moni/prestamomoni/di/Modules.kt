package com.moni.prestamomoni.di

import com.moni.prestamomoni.domain.usecase.CanApplyForLoanUsecase
import com.moni.prestamomoni.domain.usecase.FakeCanApplyForLoanUsecaseImpl
import com.moni.prestamomoni.presentation.viewmodel.CanApplyForLoanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moni_module = module {


    // Usecases
    single<CanApplyForLoanUsecase> { FakeCanApplyForLoanUsecaseImpl() }


    // Viewmodels
    viewModel {
        CanApplyForLoanViewModel(canApplyForLoanUsecase = get())
    }
}