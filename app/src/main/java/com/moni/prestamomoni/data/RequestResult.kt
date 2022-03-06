package com.moni.prestamomoni.data

import com.google.gson.annotations.SerializedName

data class RequestResult (
    val status : String? = null,
    @SerializedName("has_error")
    val isError : Boolean? = null
    )
