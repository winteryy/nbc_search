package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.SearchImageEntity
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import javax.inject.Inject


class GetSearchImageUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): GetSearchImageUseCase {

    override suspend fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): SearchImageEntity {
        return searchRepository.getSearchImage(query, sort, page, size)
    }

}