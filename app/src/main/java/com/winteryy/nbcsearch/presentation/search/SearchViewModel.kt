package com.winteryy.nbcsearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchImageUseCase: GetSearchImageUseCase
): ViewModel() {

    private var _list = MutableLiveData<List<SearchListItem>>()
    val list: LiveData<List<SearchListItem>> get() = _list

    fun getListItem(query: String) {
        viewModelScope.launch {
            _list.value = getSearchImageUseCase(query).documents?.map {
                SearchListItem(
                    thumbnailUrl = it.thumbnailUrl ?: "",
                    siteName = it.displaySiteName ?: "",
                    datetime = it.datetime,
                    isFavorite = true
                )
            }.orEmpty()
        }
    }
}