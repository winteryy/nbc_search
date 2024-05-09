package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.DocumentEntity.*
import com.winteryy.nbcsearch.domain.entity.SearchEntity

interface GetSearchVideoUseCase {

    suspend operator fun invoke(
        query: String,
        sort: String = "recency",
        page: Int = 1,
        size: Int = 15
    ): SearchEntity<VideoDocumentEntity>

}