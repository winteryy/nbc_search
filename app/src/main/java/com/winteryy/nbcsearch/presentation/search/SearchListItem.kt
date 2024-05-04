package com.winteryy.nbcsearch.presentation.search

import com.winteryy.nbcsearch.domain.entity.ContentItemEntity
import java.util.Date

data class SearchListItem(
    val thumbnailUrl: String?,
    val siteName: String?,
    val datetime: Date?,
    val isFavorite: Boolean
)

fun ContentItemEntity.toListItem() = SearchListItem(
    thumbnailUrl = thumbnailUrl,
    siteName = displaySiteName,
    datetime = datetime,
    isFavorite = isFavorite
)
