package com.winteryy.nbcsearch.presentation.storage

import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFavoriteItemMapUseCase: GetFavoriteItemMapUseCase
): ViewModel() {

    private val _favoriteList = MutableStateFlow<List<StorageListItem>?>(null)
    val favoriteList: StateFlow<List<StorageListItem>?> = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteItemMapUseCase().collectLatest { data ->
                _favoriteList.value = data.map { it.toListItem() }
            }
        }
    }
}