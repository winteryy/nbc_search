package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteItemMapUseCaseImpl @Inject constructor(
    private val storageRepository: StorageRepository
): GetFavoriteItemMapUseCase {

    override suspend fun invoke(): Flow<List<StorageEntity>> {
        return storageRepository.getFavoriteItemMap().map { itemMap ->
            itemMap.values.toList()
        }
    }

}