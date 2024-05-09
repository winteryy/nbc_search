package com.winteryy.nbcsearch.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.winteryy.nbcsearch.domain.model.LocalError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val context: Context
): LocalDataSource {

    override fun getDatsStorePref(): Flow<Preferences> {
        return context.dataStore.data
            .catch {
                throw LocalError("저장된 데이터를 불러올 수 없습니다.")
            }

    }

    override suspend fun insertToDataStore(key: Preferences.Key<String>, itemContent: String) {
        try {
            context.dataStore.edit {
                it[key] = itemContent
            }
        } catch (e: Exception) {
            throw LocalError("데이터 저장에 실패했습니다.")
        }
    }

    override suspend fun removeDataStorePref(key: Preferences.Key<String>) {
        try {
            context.dataStore.edit {
                it.remove(key)
            }
        } catch (e: Exception) {
            throw LocalError("데이터 삭제에 실패했습니다.")
        }
    }

    companion object {
        private const val STORAGE_DATA_STORE = "favorite_storage"
        private val Context.dataStore by preferencesDataStore(name = STORAGE_DATA_STORE)
    }
}