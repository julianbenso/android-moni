package com.moni.prestamomoni.core

data class ServerException(
    override val message: String
) : Exception(message)