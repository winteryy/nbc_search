package com.winteryy.nbcsearch.domain.usecase

interface RemoveFavoriteItemUseCase {
    suspend operator fun invoke(id: String)
}