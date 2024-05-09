package com.winteryy.nbcsearch.data.datasource.local

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getDatsStorePref(): Flow<Preferences>
    suspend fun insertToDataStore(key: Preferences.Key<String>, itemContent: String)
    suspend fun removeDataStorePref(key: Preferences.Key<String>)
}