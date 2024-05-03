package com.winteryy.nbcsearch.data.datasource.remote.api

import com.winteryy.nbcsearch.data.model.SearchImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80
    ): SearchImageResponse

}