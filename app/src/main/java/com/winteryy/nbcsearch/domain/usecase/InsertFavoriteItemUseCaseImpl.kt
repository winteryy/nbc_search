package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import javax.inject.Inject

class InsertFavoriteItemUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
): InsertFavoriteItemUseCase {
    override suspend fun insertFavoriteItem(item: StorageEntity) {
        storageRepository.insertFavoriteItem(item)
    }
}