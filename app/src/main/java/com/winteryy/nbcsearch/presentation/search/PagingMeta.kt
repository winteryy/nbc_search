package com.winteryy.nbcsearch.presentation.search

data class PagingMeta(
    val keyword: String,
    val imagePage: Int,
    val videoPage: Int,
    val imageIsEnd: Boolean,
    val videoIsEnd: Boolean,
)
