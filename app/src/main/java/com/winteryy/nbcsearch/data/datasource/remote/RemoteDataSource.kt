package com.winteryy.nbcsearch.data.datasource.remote

import com.winteryy.nbcsearch.data.model.SearchImageResponse

interface RemoteDataSource {

    suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchImageResponse

}