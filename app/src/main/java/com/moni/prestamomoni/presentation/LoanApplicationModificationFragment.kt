package com.moni.prestamomoni.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moni.prestamomoni.R
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.usecase.RemoveLoanApplyUsecase
import com.moni.prestamomoni.domain.usecase.SaveLoanAplicationUsecase
import org.koin.android.ext.android.inject

class LoanApplicationModificationFragment (val loan: Loan) : Fragment() {

    private val removeLoanApplyUsecase: RemoveLoanApplyUsecase by inject()
    private val saveLoanAplicationUsecase : SaveLoanAplicationUsecase by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan_application_modification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newName = view.findViewById<EditText>(R.id.et_nombre)
            newName.setText(loan.name)
        val newLast = view.findViewById<EditText>(R.id.et_apellido)
            newLast.setText(loan.last)
        val newEmail = view.findViewById<EditText>(R.id.et_email)
            newEmail.setText(loan.email)
        val newGenre = view.findViewById<EditText>(R.id.et_genero)
            newGenre.setText(loan.genre)
        var dniOld = view.findViewById<TextView>(R.id.et_dni)
        dniOld.text= loan.dni

        view.findViewById<Button>(R.id.btn_modificar_solicitud).setOnClickListener {
            removeLoanApplyUsecase.call(Dni(dniOld.toString()))
            saveLoanAplicationUsecase.call(
                Loan(
                    newName.text.toString(),
                    newLast.text.toString(),
                    dniOld.text.toString(),
                    newEmail.text.toString(),
                    newGenre.text.toString(),
                    loan.loanStatus
                )
            )
        }
    }

    companion object{
        val tag = "LoanApplicationModificationFragmentTAG"
    }

}