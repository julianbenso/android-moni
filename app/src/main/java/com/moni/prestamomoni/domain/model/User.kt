package com.moni.prestamomoni.domain.model

data class User(
    val nombre: String,
    val apelido: String,
    val nroDocuento: Dni
)