package com.winteryy.nbcsearch.domain.repository

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun getFavoriteItemMap(): Flow<HashMap<String, StorageEntity>>
    suspend fun insertFavoriteItem(item: StorageEntity)
    suspend fun removeFavoriteItem(id: String)
}