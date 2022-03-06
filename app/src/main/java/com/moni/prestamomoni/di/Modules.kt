package com.moni.prestamomoni.di


import com.moni.prestamomoni.data.*
import com.moni.prestamomoni.domain.usecase.*
import com.moni.prestamomoni.presentation.viewmodel.CanApplyForLoanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moni_module = module {

    /*single {
        CanApplyForLoanApiClient<LoanApiInterface>()
    }*/
    //factory<CanApplyForLoanUsecase> { CanApplyForLoanUsecaseImpl(loanRepository = get()) }
    single<LoanRepository> { LoanRepositoryImpl(loanRemoteSource = get()) }
    single<LoanRemoteSource> { LoanRemoteSourceImpl() }




    // Usecases
    //single<CanApplyForLoanUsecase> { CanApplyForLoanUsecaseImpl( loanRepository=get()) }
    single<CanApplyForLoanUsecase> { FakeCanApplyForLoanUsecaseImpl() }
    single<LoanAplicationListUsecase> { FakeLoanAplicationListUsecaseImpl()  }


    // Viewmodels
    viewModel { CanApplyForLoanViewModel(canApplyForLoanUsecase = get()) }
}