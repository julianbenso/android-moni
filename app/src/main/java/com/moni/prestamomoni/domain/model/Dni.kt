package com.moni.prestamomoni.domain.model

data class Dni(val digits : String) {
    init {
        require(digits.length in 8..9) { "dni debe tener entre 8 y 9 digitos" }
        require(digits.toLongOrNull() != null) { "dni debe ser numérico" }
    }
}