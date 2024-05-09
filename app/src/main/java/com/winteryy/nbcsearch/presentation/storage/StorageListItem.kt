package com.winteryy.nbcsearch.presentation.storage

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.presentation.common.ItemType
import com.winteryy.nbcsearch.presentation.common.toItemType
import java.util.Date

data class StorageListItem (
    val thumbnailUrl: String?,
    val title: String?,
    val datetime: Date?,
    val addedTime: Long,
    val itemType: ItemType
)

fun StorageEntity.toListItem() = StorageListItem(
    thumbnailUrl = thumbnailUrl,
    title = title,
    datetime = datetime,
    addedTime = addedTime,
    itemType = itemEntityType.toItemType()
)
