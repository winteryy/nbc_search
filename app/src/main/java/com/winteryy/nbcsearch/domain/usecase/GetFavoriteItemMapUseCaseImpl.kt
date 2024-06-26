package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteItemMapUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
): GetFavoriteItemMapUseCase {

    override fun invoke(): Flow<HashMap<String, StorageEntity>> {
        return storageRepository.getFavoriteItemMap()
    }

}