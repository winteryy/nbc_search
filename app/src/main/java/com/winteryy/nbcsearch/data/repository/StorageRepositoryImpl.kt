package com.winteryy.nbcsearch.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.winteryy.nbcsearch.data.datasource.local.LocalDataSource
import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : StorageRepository {

    private val gson = Gson()

    override suspend fun getFavoriteItemMap(): Flow<HashMap<String, StorageEntity>> {
        return localDataSource.getDatsStorePref().map { preferences ->
            val result = HashMap<String, StorageEntity>()
            preferences.asMap().forEach { entry ->
                result[entry.key.name] = gson.fromJson(entry.value as String, StorageEntity::class.java)
            }
            result
        }
    }

    override suspend fun insertFavoriteItem(item: StorageEntity) {
        localDataSource.insertToDataStore(stringPreferencesKey(item.thumbnailUrl!!), gson.toJson(item))
    }

    override suspend fun removeFavoriteItem(id: String) {
        localDataSource.removeDataStorePref(stringPreferencesKey(id))
    }
}