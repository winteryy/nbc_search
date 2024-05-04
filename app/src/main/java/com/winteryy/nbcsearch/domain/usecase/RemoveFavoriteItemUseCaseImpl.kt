package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.repository.StorageRepository
import javax.inject.Inject

class RemoveFavoriteItemUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
): RemoveFavoriteItemUseCase {

    override suspend fun invoke(id: String) {
        storageRepository.removeFavoriteItem(id)
    }

}