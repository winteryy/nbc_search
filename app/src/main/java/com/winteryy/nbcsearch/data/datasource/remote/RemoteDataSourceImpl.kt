package com.winteryy.nbcsearch.data.datasource.remote

import com.winteryy.nbcsearch.data.datasource.remote.api.SearchService
import com.winteryy.nbcsearch.data.model.ImageDocumentResponse
import com.winteryy.nbcsearch.data.model.SearchResponse
import com.winteryy.nbcsearch.data.model.VideoDocumentResponse
import com.winteryy.nbcsearch.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : RemoteDataSource {

    override suspend fun getSearchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchResponse<ImageDocumentResponse> {
        return try {
            searchService.searchImage(
                query = query,
                sort = sort,
                page = page,
                size = size
            )
        } catch (e: Exception) {
            when(e) {
                is HttpException -> throw NetworkError("서버로부터 정상적인 응답을 받지 못했습니다.")
                is IOException -> throw NetworkError("네트워크 연결에 실패했습니다. 연결 상태를 확인해주세요.")
                else -> throw NetworkError("네트워크 에러가 발생했습니다.")
            }
        }
    }

    override suspend fun getSearchVideo(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchResponse<VideoDocumentResponse> {
        return try {
            searchService.searchVideo(
                query = query,
                sort = sort,
                page = page,
                size = size
            )
        } catch (e: Exception) {
            when(e) {
                is HttpException -> throw NetworkError("서버로부터 정상적인 응답을 받지 못했습니다.")
                is IOException -> throw NetworkError("네트워크 연결에 실패했습니다. 연결 상태를 확인해주세요.")
                else -> throw NetworkError("네트워크 에러가 발생했습니다.")
            }
        }
    }

}