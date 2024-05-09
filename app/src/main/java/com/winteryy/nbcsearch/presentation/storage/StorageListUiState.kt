package com.winteryy.nbcsearch.presentation.storage

data class StorageListUiState(
    val list: List<StorageListItem>,
) {
    companion object {
        fun init() = StorageListUiState(
            list = emptyList(),
        )
    }
}
