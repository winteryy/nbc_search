package com.winteryy.nbcsearch.presentation.search

import com.winteryy.nbcsearch.domain.entity.ImageDocumentEntity
import com.winteryy.nbcsearch.domain.entity.StorageEntity
import java.util.Date

data class SearchListItem(
    val thumbnailUrl: String?,
    val siteName: String?,
    val datetime: Date?,
    val isFavorite: Boolean
)

fun SearchListItem.toStorageEntity() = StorageEntity(
    thumbnailUrl = thumbnailUrl,
    displaySiteName = siteName,
    datetime = datetime,
    addedTime = System.currentTimeMillis()
)

fun ImageDocumentEntity.toListItem(isFavorite: Boolean) = SearchListItem(
    thumbnailUrl = thumbnailUrl,
    siteName = displaySiteName,
    datetime = datetime,
    isFavorite = isFavorite
)
