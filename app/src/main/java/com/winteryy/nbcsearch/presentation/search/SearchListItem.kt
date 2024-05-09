package com.winteryy.nbcsearch.presentation.search

import com.winteryy.nbcsearch.domain.entity.DocumentEntity
import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.presentation.common.ItemType
import com.winteryy.nbcsearch.presentation.common.toEntity
import com.winteryy.nbcsearch.presentation.common.toItemType
import java.util.Date

data class SearchListItem(
    val thumbnailUrl: String?,
    val title: String?,
    val datetime: Date?,
    val isFavorite: Boolean,
    val itemType: ItemType
)

fun SearchListItem.toStorageEntity() = StorageEntity(
    thumbnailUrl = thumbnailUrl,
    title = title,
    datetime = datetime,
    addedTime = System.currentTimeMillis(),
    itemEntityType = itemType.toEntity()
)

fun DocumentEntity.toListItem(isFavorite: Boolean) = SearchListItem(
    thumbnailUrl = thumbnailUrl,
    title = title,
    datetime = datetime,
    isFavorite = isFavorite,
    itemType = itemEntityType.toItemType()
)