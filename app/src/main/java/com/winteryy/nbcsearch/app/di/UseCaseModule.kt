package com.winteryy.nbcsearch.app.di

import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCase
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCaseImpl
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

}