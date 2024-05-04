package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.ContentItemEntity
import com.winteryy.nbcsearch.domain.entity.ResultEntity
import com.winteryy.nbcsearch.domain.entity.SearchImageEntity
import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchImageUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository,
    private val storageRepository: StorageRepository
): GetSearchImageUseCase {

    private var cachedSearchResult: SearchImageEntity? = null

    override suspend fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ResultEntity> {

        val favoriteFlow = storageRepository.getFavoriteItemMap()

        val searchResult = searchRepository.getSearchImage(
            query = query,
            sort = sort,
            page = page,
            size = size
        )
        cachedSearchResult = searchResult

        return favoriteFlow.map { favorites ->
            updateSearchResultWithFavorites(favorites)
        }
    }

    private fun updateSearchResultWithFavorites(
        favorites: HashMap<String, StorageEntity>
    ): ResultEntity {
        return ResultEntity(
            meta = cachedSearchResult?.meta,
            contentItems = cachedSearchResult?.documents?.map { imageDocumentEntity ->
                ContentItemEntity(
                    thumbnailUrl = imageDocumentEntity.thumbnailUrl,
                    displaySiteName = imageDocumentEntity.displaySiteName,
                    datetime = imageDocumentEntity.datetime,
                    isFavorite = favorites.containsKey(imageDocumentEntity.thumbnailUrl)
                )
            }
        )
    }

}