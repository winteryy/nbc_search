package com.winteryy.nbcsearch.presentation.storage

import com.winteryy.nbcsearch.domain.entity.StorageEntity
import java.util.Date

data class StorageListItem (
    val thumbnailUrl: String?,
    val siteName: String?,
    val datetime: Date?,
)

fun StorageEntity.toListItem() = StorageListItem(
    thumbnailUrl = thumbnailUrl,
    siteName = displaySiteName,
    datetime = datetime
)
