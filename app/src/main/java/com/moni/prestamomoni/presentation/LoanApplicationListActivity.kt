package com.moni.prestamomoni.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moni.prestamomoni.R

class LoanApplicationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_aplication_list)
        supportFragmentManager.beginTransaction().replace(R.id.container_loan_aplication_list,LoanAplicationListFragment())
    }
}