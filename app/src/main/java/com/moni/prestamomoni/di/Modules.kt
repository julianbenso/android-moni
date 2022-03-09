package com.moni.prestamomoni.di


import com.moni.prestamomoni.data.*
import com.moni.prestamomoni.data.api.LoanApiInterface
import com.moni.prestamomoni.data.api.LoanAplicationListApiInterface
import com.moni.prestamomoni.data.apiclient.ApiClient
import com.moni.prestamomoni.domain.usecase.*
import com.moni.prestamomoni.presentation.viewmodel.CanApplyForLoanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moni_module = module {

    single<LoanApiInterface> { ApiClient().createRetrofit().create(LoanApiInterface::class.java) }
    single<LoanAplicationListApiInterface> { ApiClient().createRetrofit().create(LoanAplicationListApiInterface::class.java) }
    single<LoanRepository> { LoanRepositoryImpl(loanRemoteSource = get()) }
    single<LoanRemoteSource> { LoanRemoteSourceImpl(loanApiInterface = get()) }


    // Usecases
    single<CanApplyForLoanUsecase> { CanApplyForLoanUsecaseImpl( loanRepository=get()) }
    single<LoanAplicationListUsecase> { LoanAplicationListUsecaseImpl(loanAplicationListApiInterface = get()) }
    single<SaveLoanAplicationUsecase> { SaveLoanAplicationUsecaseImpl(loanAplicationListApiInterface = get()) }
    single<RemoveLoanApplyUsecase> {RemoveLoanApplyUsecaseImpl(loanAplicationListApiInterface = get())}


    // Viewmodels
    viewModel {
        CanApplyForLoanViewModel(
            canApplyForLoanUsecase = get(),
            saveloanAplicationUsecase = get()
        )
    }

}