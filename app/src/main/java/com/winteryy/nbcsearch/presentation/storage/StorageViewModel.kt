package com.winteryy.nbcsearch.presentation.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemMapUseCase
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCase
import com.winteryy.nbcsearch.presentation.common.ErrorEvent
import com.winteryy.nbcsearch.presentation.common.ErrorViewModel
import com.winteryy.nbcsearch.presentation.common.toErrorEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFavoriteItemMapUseCase: GetFavoriteItemMapUseCase,
    private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase
): ViewModel(), ErrorViewModel {

    private val _favoriteList = MutableStateFlow(StorageListUiState.init())
    val favoriteList: StateFlow<StorageListUiState> = _favoriteList.asStateFlow()

    private val _errorEvent = MutableSharedFlow<ErrorEvent>()
    override val errorEvent: SharedFlow<ErrorEvent> get() = _errorEvent

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
            }.catch {
                _errorEvent.emit(it.toErrorEvent())
            }
            .launchIn(viewModelScope)
    }

    fun removeItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                removeFavoriteItemUseCase(id)
            }.onFailure {
                _errorEvent.emit(it.toErrorEvent())
            }
        }
    }
}