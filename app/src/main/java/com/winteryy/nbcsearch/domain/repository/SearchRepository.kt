package com.winteryy.nbcsearch.domain.repository

import com.winteryy.nbcsearch.domain.entity.DocumentEntity.*
import com.winteryy.nbcsearch.domain.entity.SearchEntity

interface SearchRepository {

    suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchEntity<ImageDocumentEntity>

    suspend fun getSearchVideo(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchEntity<VideoDocumentEntity>

}