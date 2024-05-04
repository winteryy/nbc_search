package com.winteryy.nbcsearch.presentation.storage

data class StorageListUiState(
    val list: List<StorageListItem>,
    val isLoading: Boolean
) {
    companion object {
        fun init() = StorageListUiState(
            list = emptyList(),
            isLoading = false
        )
    }
}
