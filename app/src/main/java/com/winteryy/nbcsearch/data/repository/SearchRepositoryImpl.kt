package com.winteryy.nbcsearch.data.repository

import com.winteryy.nbcsearch.data.datasource.remote.RemoteDataSource
import com.winteryy.nbcsearch.data.model.toImageEntity
import com.winteryy.nbcsearch.data.model.toVideoEntity
import com.winteryy.nbcsearch.domain.entity.DocumentEntity
import com.winteryy.nbcsearch.domain.entity.SearchEntity
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
    ): SearchEntity<DocumentEntity.ImageDocumentEntity> {
        return remoteDataSource.getSearchImage(
            query = query,
            sort = sort,
            page = page,
            size = size
        ).toImageEntity()
    }

    override suspend fun getSearchVideo(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchEntity<DocumentEntity.VideoDocumentEntity> {
        return remoteDataSource.getSearchVideo(
            query = query,
            sort = sort,
            page = page,
            size = size
        ).toVideoEntity()
    }

}