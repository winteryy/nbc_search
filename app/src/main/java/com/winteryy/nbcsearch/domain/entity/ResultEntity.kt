package com.winteryy.nbcsearch.domain.entity

import java.util.Date

data class ResultEntity(
    val meta: MetaEntity,
    val contentItems: List<ContentItemEntity>
)

data class ContentItemEntity(
    val thumbnailUrl: String,
    val displaySiteName: String,
    val datetime: Date,
    val isFavorite: Boolean
)
