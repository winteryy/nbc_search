package com.winteryy.nbcsearch.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SearchResponse<T>(
    @SerializedName("meta")
    val meta: MetaResponse?,
    @SerializedName("documents")
    val documents: List<T>?
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

data class VideoDocumentResponse(
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("datetime")
    val datetime: Date?,
    @SerializedName("play_time")
    val playTime: Int?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("author")
    val author: String?
)

