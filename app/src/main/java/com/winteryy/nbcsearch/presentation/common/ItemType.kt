package com.winteryy.nbcsearch.presentation.common

import com.winteryy.nbcsearch.domain.entity.ItemEntityType

enum class ItemType {
    IMAGE, VIDEO
}

fun ItemType.toEntity(): ItemEntityType {
    return when(this) {
        ItemType.IMAGE -> ItemEntityType.IMAGE
        ItemType.VIDEO -> ItemEntityType.VIDEO
    }
}

fun ItemEntityType.toItemType(): ItemType {
    return when(this) {
        ItemEntityType.IMAGE -> ItemType.IMAGE
        ItemEntityType.VIDEO -> ItemType.VIDEO
    }
}
