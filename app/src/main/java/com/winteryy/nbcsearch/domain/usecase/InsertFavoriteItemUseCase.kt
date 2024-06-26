package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity

interface InsertFavoriteItemUseCase {

    suspend operator fun invoke(item: StorageEntity)

}