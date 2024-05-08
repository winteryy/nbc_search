package com.winteryy.nbcsearch.presentation.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemMapUseCase
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFavoriteItemMapUseCase: GetFavoriteItemMapUseCase,
    private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase
): ViewModel() {

    private val _favoriteList = MutableStateFlow(StorageListUiState.init())
    val favoriteList: StateFlow<StorageListUiState> = _favoriteList.asStateFlow()

    private val favoriteFlow = getFavoriteItemMapUseCase()

    init {
        favoriteFlow
            .onEach { itemMap ->
                val newFavoriteItemList = itemMap.values
                    .map { it.toListItem() }
                    .sortedByDescending { it.addedTime }

                    _favoriteList.update { prev ->
                        prev.copy(list = newFavoriteItemList)
                    }
            }
            .launchIn(viewModelScope)
    }

    fun removeItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavoriteItemUseCase(id)
        }
    }
}