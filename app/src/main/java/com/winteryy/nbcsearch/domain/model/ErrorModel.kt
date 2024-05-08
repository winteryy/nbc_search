package com.winteryy.nbcsearch.domain.model

data class NetworkError(
    val msg: String
): Error(msg)

data class LocalError(
    val msg: String
): Error(msg)