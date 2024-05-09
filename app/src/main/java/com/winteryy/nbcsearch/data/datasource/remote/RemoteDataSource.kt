package com.winteryy.nbcsearch.data.datasource.remote

import com.winteryy.nbcsearch.data.model.ImageDocumentResponse
import com.winteryy.nbcsearch.data.model.SearchResponse
import com.winteryy.nbcsearch.data.model.VideoDocumentResponse

interface RemoteDataSource {

    suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchResponse<ImageDocumentResponse>

    suspend fun getSearchVideo(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): SearchResponse<VideoDocumentResponse>

}