package com.winteryy.nbcsearch.presentation.search

data class SearchListUiState(
    val list: List<SearchListItem>
) {
    companion object {
        fun init() = SearchListUiState(
            list = emptyList()
        )
    }

}
