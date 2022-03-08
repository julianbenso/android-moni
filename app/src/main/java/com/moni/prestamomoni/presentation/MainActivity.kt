package com.moni.prestamomoni.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.moni.prestamomoni.R
import com.moni.prestamomoni.data.dto.RegistrationResult
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
        uiData.showSaveLoanAplicationResult?.consume()?.let { showSaveLoanAplicationResult(it) }
    }

    private lateinit var msjStatusLoan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val name: EditText = findViewById(R.id.nombre)
        val lastName: EditText = findViewById(R.id.apellido)
        val email: EditText = findViewById(R.id.email)
        val genero: EditText = findViewById(R.id.genero)
        val dni: EditText = findViewById(R.id.dni)


        viewModel.uiData.observe(this, uiDataObserver)

        msjStatusLoan = findViewById(R.id.textView)

        findViewById<View>(R.id.button).setOnClickListener {
            viewModel.canApplyForLoan(
                name.text.toString(),
                lastName.text.toString(),
                dni.text.toString(),
                email.text.toString(),
                genero.text.toString()
            )
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, LoanApplicationListActivity::class.java))
        }
    }


    private fun showLoanStatus(it: LoanStatus) {
        msjStatusLoan.visibility = View.VISIBLE
        msjStatusLoan.text = resources.getString(R.string.estado_crediticio, it.toString())
    }

    private fun showLoading(it: Boolean) {
        val progressView = findViewById<View>(R.id.progressbar)
        progressView.visibility = if (it) View.VISIBLE else View.GONE
        if (it) msjStatusLoan.visibility = View.GONE
    }

    private fun showError(it: Throwable) {
        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
    }

    private fun showSaveLoanAplicationResult(result: RegistrationResult) {
        val text = when (result) {
            RegistrationResult.OK -> getString(R.string.registration_result_message_ok)
            RegistrationResult.FAIL -> getString(R.string.registration_result_message_fail)
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


}