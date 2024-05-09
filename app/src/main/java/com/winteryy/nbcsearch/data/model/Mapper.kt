package com.winteryy.nbcsearch.data.model

import com.winteryy.nbcsearch.domain.entity.DocumentEntity.*
import com.winteryy.nbcsearch.domain.entity.ItemEntityType
import com.winteryy.nbcsearch.domain.entity.MetaEntity
import com.winteryy.nbcsearch.domain.entity.SearchEntity

fun SearchResponse<ImageDocumentResponse>.toImageEntity() = SearchEntity(
    meta = meta?.toEntity(),
    documents = documents?.map {
        it.toEntity()
    }
)

fun SearchResponse<VideoDocumentResponse>.toVideoEntity() = SearchEntity(
    meta = meta?.toEntity(),
    documents = documents?.map {
        it.toEntity()
    }
)

fun MetaResponse.toEntity() = MetaEntity(
    isEnd = isEnd,
    pageableCount = pageableCount,
    totalCount = totalCount,
)

fun ImageDocumentResponse.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    width = width,
    height = height,
    title = displaySiteName,
    docUrl = docUrl,
    datetime = datetime,
    itemEntityType = ItemEntityType.IMAGE
)

fun VideoDocumentResponse.toEntity() = VideoDocumentEntity(
    title = title,
    url = url,
    datetime = datetime,
    playTime = playTime,
    width = width,
    thumbnailUrl = thumbnail,
    author = author,
    itemEntityType = ItemEntityType.VIDEO
)