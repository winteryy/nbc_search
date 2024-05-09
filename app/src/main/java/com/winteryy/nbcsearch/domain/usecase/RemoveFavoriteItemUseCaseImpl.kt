package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.repository.StorageRepository
import javax.inject.Inject

class RemoveFavoriteItemUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
): RemoveFavoriteItemUseCase {

    /**
     * 보관함에서 선택한 아이템(id: thumbnail_url 로 식별)을 제거하는 UseCase
     */
    override suspend fun invoke(id: String) {
        storageRepository.removeFavoriteItem(id)
    }

}