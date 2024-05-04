package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import kotlinx.coroutines.flow.Flow

interface GetFavoriteItemListUseCase {

    suspend operator fun invoke(): Flow<List<StorageEntity>>

}