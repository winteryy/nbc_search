package com.winteryy.nbcsearch.domain.repository

import com.winteryy.nbcsearch.domain.entity.SearchImageEntity

interface SearchRepository {

    suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchImageEntity
}