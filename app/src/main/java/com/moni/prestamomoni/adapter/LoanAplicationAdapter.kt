package com.moni.prestamomoni.adapter

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moni.prestamomoni.R
import com.moni.prestamomoni.domain.model.Loan

class LoanAplicationAdapter (
    val items : List<Loan>,
    val oicl : (Loan)-> Unit
) : RecyclerView.Adapter<LoanAplicationAdapter.ViewHolder>()

{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoanAplicationAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loan_aplication,parent,false))
    }

    override fun onBindViewHolder(holder: LoanAplicationAdapter.ViewHolder, position: Int) {
        val res : Resources = holder.itemView.resources

        holder.personName.text = res.getString(R.string.item_titular_nombre,items[position].nombre, items[position].apellido)
        holder.statusLoan.text = res.getString(R.string.item_status, items[position].estadoPresatamo)
        holder.dni.text = res.getString(R.string.item_titular_dni, items[position].dni)
        holder.email.text = res.getString(R.string.item_titular_email, items[position].email)
        holder.genero.text = res.getString(R.string.item_titular_genero, items[position].genero)
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
