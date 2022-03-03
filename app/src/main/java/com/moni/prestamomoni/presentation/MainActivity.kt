package com.moni.prestamomoni.presentation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.moni.prestamomoni.R
import com.moni.prestamomoni.domain.model.LoanStatus
import com.moni.prestamomoni.presentation.uimodel.CanApplyForLoanUiData
import com.moni.prestamomoni.presentation.viewmodel.CanApplyForLoanViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: CanApplyForLoanViewModel by viewModel()

    private val uiDataObserver = Observer<CanApplyForLoanUiData> { uiData ->
        uiData.showError?.consume()?.let { showError(it) }
        uiData.showLoading?.consume()?.let { showLoading(it) }
        uiData.showLoanStatus?.consume()?.let { showLoanStatus(it) }
    }


    private fun showLoanStatus(it: LoanStatus) {
        findViewById<TextView>(R.id.textView).text = it.toString()
        findViewById<TextView>(R.id.textView).setTextColor(resources.getColor(R.color.black))
    }

    private fun showLoading(it: Boolean) {
        val progressView = findViewById<View>(R.id.progressbar)
        progressView.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun showError(it: Throwable) {
        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.uiData.observe(this, uiDataObserver)

        findViewById<View>(R.id.button).setOnClickListener {
            val dniText = findViewById<EditText>(R.id.dni).text.toString()
            viewModel.canApplyForLoan(dniText)
        }
    }
}