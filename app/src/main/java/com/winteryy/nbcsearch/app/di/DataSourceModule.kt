package com.winteryy.nbcsearch.app.di

import android.content.Context
import com.winteryy.nbcsearch.data.datasource.local.LocalDataSource
import com.winteryy.nbcsearch.data.datasource.local.LocalDataSourceImpl
import com.winteryy.nbcsearch.data.datasource.remote.RemoteDataSource
import com.winteryy.nbcsearch.data.datasource.remote.RemoteDataSourceImpl
import com.winteryy.nbcsearch.data.datasource.remote.api.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSourceModule(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSourceImpl(context)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSourceModule(searchService: SearchService): RemoteDataSource {
        return RemoteDataSourceImpl(searchService)
    }

}