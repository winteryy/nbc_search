package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.SearchImageEntity

interface GetSearchImageUseCase {

    suspend operator fun invoke(
        query: String,
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 80
    ): SearchImageEntity

}