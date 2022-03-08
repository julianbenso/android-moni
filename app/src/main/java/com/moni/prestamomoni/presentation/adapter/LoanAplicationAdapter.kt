package com.moni.prestamomoni.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.moni.prestamomoni.R
import com.moni.prestamomoni.domain.model.Dni
import com.moni.prestamomoni.domain.model.Loan
import com.moni.prestamomoni.domain.usecase.RemoveLoanApplyUsecase
import com.moni.prestamomoni.presentation.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoanAplicationAdapter (
    val items : List<Loan>,
    val oicl : (Loan)-> Unit
) : KoinComponent, RecyclerView.Adapter<LoanAplicationAdapter.ViewHolder>()
{
    private val mRemoveLoanApplyUsecase : RemoveLoanApplyUsecase by inject()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoanAplicationAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loan_aplication,parent,false))
    }

    override fun onBindViewHolder(holder: LoanAplicationAdapter.ViewHolder, position: Int) {
        val res : Resources = holder.itemView.resources

        holder.personName.text = res.getString(R.string.item_titular_nombre,items[position].name, items[position].last)
        holder.statusLoan.text = res.getString(R.string.item_status, items[position].loanStatus)
        holder.dni.text = res.getString(R.string.item_titular_dni, items[position].dni)
        holder.email.text = res.getString(R.string.item_titular_email, items[position].email)
        holder.genero.text = res.getString(R.string.item_titular_genero, items[position].genre)

        holder.itemView.findViewById<Button>(R.id.btn_remove).setOnClickListener {
            val numDni :String = holder.dni.text.toString().filter{it.isDigit()} //Obtengo del String "DNI: 12345678" -> "12345678"
            Toast.makeText(holder.itemView.context,"Se deberia llamar a la  peticion de eliminacion con dni : $numDni",Toast.LENGTH_SHORT).show()
            mRemoveLoanApplyUsecase.call(Dni(numDni))
        }

        holder.itemView.findViewById<Button>(R.id.btn_modify).setOnClickListener {
            val fragmentManager = LoanApplicationListActivity().supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.container_loan_aplication_list,LoanApplicationModificationFragment(
                Loan(
                    items[position].name,
                    items[position].last,
                    items[position].dni,
                    items[position].email,
                    items[position].genre,
                    items[position].loanStatus)
            ))

        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val statusLoan = itemView.findViewById<TextView>(R.id.tv_item_status_loan)
        val personName = itemView.findViewById<TextView>(R.id.tv_item_name_person)
        val dni = itemView.findViewById<TextView>(R.id.tv_item_dni)
        val email = itemView.findViewById<TextView>(R.id.tv_item_email)
        val genero = itemView.findViewById<TextView>(R.id.tv_item_gender)
    }

}
