package com.winteryy.nbcsearch.domain.entity

import java.util.Date

data class SearchImageEntity(
    val meta: MetaEntity?,
    val documents: List<ImageDocumentEntity>?
)

data class MetaEntity(
    val isEnd: Boolean?,
    val pageableCount: Int?,
    val totalCount: Int?
)

data class ImageDocumentEntity(
    val collection: String?,
    val thumbnailUrl: String?,
    val imageUrl: String?,
    val width: Int?,
    val height: Int?,
    val displaySiteName: String?,
    val docUrl: String?,
    val datetime: Date?
)