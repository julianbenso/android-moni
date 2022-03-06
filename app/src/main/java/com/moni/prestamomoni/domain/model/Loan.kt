package com.moni.prestamomoni.domain.model

data class Loan (
    val nombre : String? = null,
    val apellido : String? = null,
    val dni : String? = null,
    val email : String? = null,
    val genero : String? = null,
    var estadoPresatamo : String? = null
)