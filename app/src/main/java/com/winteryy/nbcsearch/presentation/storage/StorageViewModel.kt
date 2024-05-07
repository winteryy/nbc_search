package com.winteryy.nbcsearch.presentation.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemListUseCase
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFavoriteItemListUseCase: GetFavoriteItemListUseCase,
    private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase
): ViewModel() {

    private val _favoriteList = MutableStateFlow(StorageListUiState.init())
    val favoriteList: StateFlow<StorageListUiState> = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteItemListUseCase().collectLatest { data ->
                _favoriteList.update { prev ->
                    prev.copy( list = data.map { it.toListItem() }.sortedByDescending { it.addedTime } )
                }
            }
        }
    }

    fun removeItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavoriteItemUseCase(id)
        }
    }
}