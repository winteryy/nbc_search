package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

interface GetSearchImageUseCase {

    suspend operator fun invoke(
        query: String,
        sort: String = "accuracy",
        page: Int = 1,
        size: Int = 80
    ): Flow<ResultEntity>

}