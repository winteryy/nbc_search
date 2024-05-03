package com.winteryy.nbcsearch.data.datasource.remote

import com.winteryy.nbcsearch.data.datasource.remote.api.SearchService
import com.winteryy.nbcsearch.data.model.SearchImageResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService
): RemoteDataSource {

    override suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchImageResponse {
        return searchService.searchImage(
            query = query,
            sort = sort,
            page = page,
            size = size
        )
    }

}