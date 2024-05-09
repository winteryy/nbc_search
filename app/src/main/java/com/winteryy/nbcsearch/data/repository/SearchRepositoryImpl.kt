package com.winteryy.nbcsearch.data.repository

import com.winteryy.nbcsearch.data.datasource.remote.RemoteDataSource
import com.winteryy.nbcsearch.data.model.toEntity
import com.winteryy.nbcsearch.domain.entity.SearchImageEntity
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): SearchRepository {

    override suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchImageEntity {
        return remoteDataSource.getSearchImage(
            query = query,
            sort = sort,
            page = page,
            size = size
        ).toEntity()
    }

}