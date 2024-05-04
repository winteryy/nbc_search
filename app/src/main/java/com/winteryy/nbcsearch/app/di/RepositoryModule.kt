package com.winteryy.nbcsearch.app.di

import com.winteryy.nbcsearch.data.repository.SearchRepositoryImpl
import com.winteryy.nbcsearch.data.repository.StorageRepositoryImpl
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Singleton
    @Binds
    abstract fun bindStorageRepository(
        storageRepositoryImpl: StorageRepositoryImpl
    ): StorageRepository

}