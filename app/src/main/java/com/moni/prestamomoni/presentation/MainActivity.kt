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
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.model.LoanStatus
import com.moni.prestamomoni.presentation.uimodel.CanApplyForLoanUiData
import com.moni.prestamomoni.presentation.viewmodel.CanApplyForLoanViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: CanApplyForLoanViewModel by viewModel()

    private val uiDataObserver = Observer<CanApplyForLoanUiData> { uiData ->
        uiData.showError?.consume()?.let { showError(it) }
        uiData.showLoading?.consume()?.let { showLoading(it) }
        uiData.showLoanStatus?.consume()?.let { showLoanStatus(it) }
    }
    private lateinit var msjStatusLoan : TextView
    lateinit var loan :Loan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val name : String = findViewById<EditText>(R.id.nombre).text.toString()
        val lastName : String = findViewById<EditText>(R.id.apellido).text.toString()
        val email : String = findViewById<EditText>(R.id.email).text.toString()
        val dni : String = findViewById<EditText>(R.id.dni).text.toString()
        val genero : String = findViewById<EditText>(R.id.genero).text.toString()


        viewModel.uiData.observe(this, uiDataObserver)

        msjStatusLoan =  findViewById(R.id.textView)

        findViewById<View>(R.id.button).setOnClickListener {
            viewModel.canApplyForLoan(dni)
            loan = Loan(name, lastName, dni, email, genero, null)
        }

        findViewById<View>(R.id.button2).setOnClickListener{
            startActivity(Intent(this,LoanApplicationListActivity::class.java))
        }
    }


    private fun showLoanStatus(it: LoanStatus) {
        msjStatusLoan.visibility = View.VISIBLE
        msjStatusLoan.text = resources.getString(R.string.estado_crediticio,it.toString())
    }

    private fun showLoading(it: Boolean) {
        val progressView = findViewById<View>(R.id.progressbar)
        progressView.visibility = if (it) View.VISIBLE else View.GONE
        msjStatusLoan.visibility = View.GONE
    }

    private fun showError(it: Throwable) {
        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
    }


}