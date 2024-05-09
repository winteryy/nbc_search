package com.winteryy.nbcsearch.domain.entity

import java.util.Date

data class StorageEntity(
    val thumbnailUrl: String?,
    val title: String?,
    val datetime: Date?,
    val addedTime: Long,
    val itemEntityType: ItemEntityType
)
