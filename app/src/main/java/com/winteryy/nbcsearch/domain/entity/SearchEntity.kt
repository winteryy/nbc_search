package com.winteryy.nbcsearch.domain.entity

import java.util.Date

data class SearchEntity<T>(
    val meta: MetaEntity?,
    val documents: List<T>?
)

data class MetaEntity(
    val isEnd: Boolean?,
    val pageableCount: Int?,
    val totalCount: Int?
)

sealed interface DocumentEntity {

    val title: String?
    val thumbnailUrl: String?
    val datetime: Date?
    val itemEntityType: ItemEntityType

    data class ImageDocumentEntity(
        val collection: String?,
        val imageUrl: String?,
        val width: Int?,
        val height: Int?,
        val docUrl: String?,
        override val title: String?,
        override val thumbnailUrl: String?,
        override val datetime: Date?,
        override val itemEntityType: ItemEntityType
    ): DocumentEntity

    data class VideoDocumentEntity(
        val url: String?,
        val playTime: Int?,
        val width: Int?,
        val author: String?,
        override val title: String?,
        override val thumbnailUrl: String?,
        override val datetime: Date?,
        override val itemEntityType: ItemEntityType
    ): DocumentEntity
}
