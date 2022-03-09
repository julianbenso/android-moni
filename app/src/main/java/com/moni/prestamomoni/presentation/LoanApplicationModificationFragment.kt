package com.moni.prestamomoni.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.moni.prestamomoni.R
import com.moni.prestamomoni.domain.model.Loan

class LoanApplicationModificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan_application_modification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loan = arguments?.getSerializable(LOAN_BUNDLE) as? Loan
            ?: error("Debes pasar como argumento un loan")

        val newName = view.findViewById<EditText>(R.id.et_nombre)
        newName.setText(loan.name)
        val newLast = view.findViewById<EditText>(R.id.et_apellido)
        newLast.setText(loan.last)
        val newEmail = view.findViewById<EditText>(R.id.et_email)
        newEmail.setText(loan.email)
        val newGenre = view.findViewById<EditText>(R.id.et_genero)
        newGenre.setText(loan.genre)
        val dniOld = view.findViewById<TextView>(R.id.et_dni)
        dniOld.text = loan.dni

        view.findViewById<Button>(R.id.btn_modificar_solicitud).setOnClickListener {
            Toast.makeText(context, R.string.proximamente, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val LOAN_BUNDLE = "LOAN_BUNDLE"
        fun newInstance(loan: Loan): LoanApplicationModificationFragment {
            return LoanApplicationModificationFragment().apply {
                arguments = bundleOf(LOAN_BUNDLE to loan)
            }
        }
    }

}