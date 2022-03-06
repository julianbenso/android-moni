package com.moni.prestamomoni.data

import com.google.gson.annotations.SerializedName

data class RequestLoanListResponse (
    @SerializedName("name")
    val nombre : String? = null,
    @SerializedName("last")
    val apellido : String? = null,
    @SerializedName("dni")
    val dni : String? = null,
    @SerializedName("email")
    val email : String? = null,
    @SerializedName("genre")
    val genero : String? = null,
    @SerializedName("loanStatus")
    val estadoPrestamo : String? = null
)