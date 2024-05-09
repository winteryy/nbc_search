package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.DocumentEntity.*
import com.winteryy.nbcsearch.domain.entity.SearchEntity
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchVideoUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): GetSearchVideoUseCase {

    override suspend fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchEntity<VideoDocumentEntity> {
        return searchRepository.getSearchVideo(query, sort, page, size)
    }


}