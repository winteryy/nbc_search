package com.winteryy.nbcsearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCase
import com.winteryy.nbcsearch.domain.usecase.InsertFavoriteItemUseCase
import com.winteryy.nbcsearch.presentation.storage.StorageListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchImageUseCase: GetSearchImageUseCase,
    private val insertFavoriteItemUseCase: InsertFavoriteItemUseCase
): ViewModel() {

    private val _searchList = MutableStateFlow<List<SearchListItem>?>(null)
    val searchList: StateFlow<List<SearchListItem>?> = _searchList.asStateFlow()

    init {
        _searchList.value = emptyList()
    }

    fun getListItem(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSearchImageUseCase(query).collectLatest { result ->
                _searchList.emit(result.contentItems?.map { it.toListItem() })
            }
        }
    }

    fun saveToStorage(item: SearchListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteItemUseCase.insertFavoriteItem(item.toStorageEntity())
        }
    }
}