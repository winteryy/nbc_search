package com.winteryy.nbcsearch.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SearchImageResponse(
    @SerializedName("meta")
    val meta: MetaResponse?,
    @SerializedName("documents")
    val documents: List<ImageDocumentResponse>?
)

data class MetaResponse(
    @SerializedName("is_end")
    val isEnd: Boolean?,
    @SerializedName("pageable_count")
    val pageableCount: Int?,
    @SerializedName("total_count")
    val totalCount: Int?
)

data class ImageDocumentResponse(
    @SerializedName("collection")
    val collection: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("display_sitename")
    val displaySiteName: String?,
    @SerializedName("doc_url")
    val docUrl: String?,
    @SerializedName("datetime")
    val datetime: Date?
)

