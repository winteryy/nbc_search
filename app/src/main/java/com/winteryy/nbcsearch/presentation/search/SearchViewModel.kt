package com.winteryy.nbcsearch.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.entity.MetaEntity
import com.winteryy.nbcsearch.domain.entity.SearchImageEntity
import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemMapUseCase
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCase
import com.winteryy.nbcsearch.domain.usecase.InsertFavoriteItemUseCase
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchImageUseCase: GetSearchImageUseCase,
    private val getFavoriteItemMapUseCase: GetFavoriteItemMapUseCase,
    private val insertFavoriteItemUseCase: InsertFavoriteItemUseCase,
    private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase
): ViewModel() {

    private val _combinedSearchList = MutableStateFlow(SearchListUiState.init())
    val combinedSearchList: StateFlow<SearchListUiState> = _combinedSearchList.asStateFlow()

    private val searchImageFlow: MutableSharedFlow<SearchImageEntity> = MutableSharedFlow()
    private val favoriteFlow: Flow<HashMap<String, StorageEntity>> = getFavoriteItemMapUseCase()

    init {
        searchImageFlow.combine(favoriteFlow) { searchImageEntity, favoriteMap ->
            searchImageEntity.documents?.map {
                if(favoriteMap.containsKey(it.thumbnailUrl)) {
                    it.toListItem(true)
                }else {
                    it.toListItem(false)
                }
            }.orEmpty()
        }.onEach { combinedList ->
            _combinedSearchList.update { prev ->
                prev.copy(list = combinedList)
            }
        }.launchIn(viewModelScope)
    }

    fun getListItem(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchResult = try {
                getSearchImageUseCase(query)
            } catch (e: Exception) {
                //예외처리 임시로
                SearchImageEntity(MetaEntity(null, null, null), emptyList())
            }
            withContext(Dispatchers.Main) {
                searchImageFlow.emit(searchResult)
            }
        }
    }

    fun saveToStorage(item: SearchListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteItemUseCase(item.toStorageEntity())
        }
    }

    fun removeFromStorage(id: String) {
        viewModelScope.launch (Dispatchers.IO) {
            removeFavoriteItemUseCase(id)
        }
    }
}