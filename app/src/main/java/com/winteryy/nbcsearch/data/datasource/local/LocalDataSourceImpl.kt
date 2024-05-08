package com.winteryy.nbcsearch.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val context: Context
): LocalDataSource {

    override fun getDatsStorePref(): Flow<Preferences> {
        return context.dataStore.data
    }

    override suspend fun insertToDataStore(key: Preferences.Key<String>, itemContent: String) {
        context.dataStore.edit {
            it[key] = itemContent
        }
    }

    override suspend fun removeDataStorePref(key: Preferences.Key<String>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }

    companion object {
        private const val STORAGE_DATA_STORE = "favorite_storage"
        private val Context.dataStore by preferencesDataStore(name = STORAGE_DATA_STORE)
    }
}