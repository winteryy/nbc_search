package com.winteryy.nbcsearch.app.di

import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemListUseCase
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemListUseCaseImpl
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCase
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCaseImpl
import com.winteryy.nbcsearch.domain.usecase.InsertFavoriteItemUseCase
import com.winteryy.nbcsearch.domain.usecase.InsertFavoriteItemUseCaseImpl
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCase
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @ViewModelScoped
    @Binds
    abstract fun bindGetSearchImageUseCase(
        getSearchImageUseCaseImpl: GetSearchImageUseCaseImpl
    ): GetSearchImageUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetFavoriteItemMapUseCase(
        getFavoriteItemMapUseCaseImpl: GetFavoriteItemListUseCaseImpl
    ): GetFavoriteItemListUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindInsertFavoriteItemUseCase(
        insertFavoriteItemUseCaseImpl: InsertFavoriteItemUseCaseImpl
    ): InsertFavoriteItemUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindRemoveFavoriteItemUseCase(
        removeFavoriteItemUseCaseImpl: RemoveFavoriteItemUseCaseImpl
    ): RemoveFavoriteItemUseCase
}