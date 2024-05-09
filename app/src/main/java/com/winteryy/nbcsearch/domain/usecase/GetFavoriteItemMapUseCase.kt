package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteItemMapUseCase {

    operator fun invoke(): Flow<HashMap<String, StorageEntity>>
}