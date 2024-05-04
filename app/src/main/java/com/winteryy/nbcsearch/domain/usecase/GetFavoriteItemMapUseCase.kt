package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteItemMapUseCase {

    suspend operator fun invoke(): Flow<List<StorageEntity>>

}