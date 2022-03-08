package com.moni.prestamomoni.domain.model

data class Loan (
    val name : String? = null,
    val last : String? = null,
    val dni : String? = null,
    val email : String? = null,
    val genre : String? = null,
    var loanStatus : String? = null
)