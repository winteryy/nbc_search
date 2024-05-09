package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.DocumentEntity.*
import com.winteryy.nbcsearch.domain.entity.SearchEntity
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import javax.inject.Inject


class GetSearchImageUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): GetSearchImageUseCase {

    /**
     * 이미지 검색 결과를 가져오는 UseCase
     */
    override suspend fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchEntity<ImageDocumentEntity> {
        return searchRepository.getSearchImage(query, sort, page, size)
    }

}