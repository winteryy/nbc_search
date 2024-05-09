package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import javax.inject.Inject

class InsertFavoriteItemUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
): InsertFavoriteItemUseCase {

    /**
     * 보관함에 선택한 아이템을 저장하는 UseCase
     */
    override suspend fun invoke(item: StorageEntity) {
        storageRepository.insertFavoriteItem(item)
    }

}