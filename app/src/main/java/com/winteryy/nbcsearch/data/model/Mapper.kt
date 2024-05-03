package com.winteryy.nbcsearch.data.model

import com.winteryy.nbcsearch.domain.entity.ImageDocumentEntity
import com.winteryy.nbcsearch.domain.entity.MetaEntity
import com.winteryy.nbcsearch.domain.entity.SearchImageEntity

fun SearchImageResponse.toEntity() = SearchImageEntity(
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
    displaySiteName = displaySiteName,
    docUrl = docUrl,
    datetime = datetime
)