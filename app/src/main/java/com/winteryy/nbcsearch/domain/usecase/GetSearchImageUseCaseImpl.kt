package com.winteryy.nbcsearch.domain.usecase

import com.winteryy.nbcsearch.domain.entity.ContentItemEntity
import com.winteryy.nbcsearch.domain.entity.ResultEntity
import com.winteryy.nbcsearch.domain.entity.SearchImageEntity
import com.winteryy.nbcsearch.domain.repository.SearchRepository
import com.winteryy.nbcsearch.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchImageUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository,
    private val storageRepository: StorageRepository
): GetSearchImageUseCase {

    override suspend fun invoke(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ResultEntity> {

        val favoriteFlow = storageRepository.getFavoriteItemMap()
        val searchResultFlow = flow {
            emit(
                searchRepository.getSearchImage(
                    query = query,
                    sort = sort,
                    page = page,
                    size = size
                )
            )
        }

        return searchResultFlow.combine(favoriteFlow) { searchResult, favorites ->
            ResultEntity(
                meta = searchResult.meta,
                contentItems = searchResult.documents?.map { imageDocumentEntity ->
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

}