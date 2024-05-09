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

    /**
     * DataStore내에 저장된 데이터를 <key, entity> 형태의 HashMap으로 매핑해서 내려보냅니다.
     * HashMap으로 보내는 이유는, 검색결과와 병합시 보관함 저장 여부를 빠르게 확인하기 위함입니다.
     */
    override fun getFavoriteItemMap(): Flow<HashMap<String, StorageEntity>> {
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