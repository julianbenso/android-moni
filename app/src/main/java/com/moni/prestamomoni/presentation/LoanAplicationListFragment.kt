package com.moni.prestamomoni.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moni.prestamomoni.R
import com.moni.prestamomoni.adapter.LoanAplicationAdapter
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.usecase.LoanAplicationListUsecase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.koin.android.ext.android.inject

class LoanAplicationListFragment : Fragment() {

    private val mLoanAplicationListUsecase : LoanAplicationListUsecase by inject()
    private lateinit var loanAplicationAdapter : LoanAplicationAdapter
    private val composite = CompositeDisposable()
    lateinit var loan : Loan

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan_aplication_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler : RecyclerView = view.findViewById(R.id.rcv_loan_list)
        val loadingList = view.findViewById<ProgressBar>(R.id.loading_list)

        //busca las solicitudes de prestamo
        mLoanAplicationListUsecase.call()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { Toast.makeText(requireContext(),"OCURRIO ERROR",Toast.LENGTH_SHORT).show()
                            loadingList.visibility = View.GONE
                          },
                onSuccess = { listLoan ->
                    loanAplicationAdapter = LoanAplicationAdapter(listLoan){onAdapter(it)}
                    recycler.adapter = loanAplicationAdapter
                    loadingList.visibility = View.GONE
                }
            )
            .addTo(composite)

        recycler.layoutManager =LinearLayoutManager(context)
        loadingList.visibility = View.VISIBLE
    }

    private fun onAdapter(item : Loan){
        loan = item
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.clear()
    }
}