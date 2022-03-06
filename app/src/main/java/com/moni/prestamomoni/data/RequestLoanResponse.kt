package com.moni.prestamomoni.data

class RequestLoanResponse<T> {

    private val result : T? =  null
    private val status : String? = null
    private val error : String? = null

    @Override
    override fun toString() :String{
        return "{\n" +
                "    \"status\": \"$status\",\n" +
                "    \"has_error\": $error\n" +
                "}"
    }
}