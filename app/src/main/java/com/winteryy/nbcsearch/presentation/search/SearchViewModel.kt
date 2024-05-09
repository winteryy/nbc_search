package com.winteryy.nbcsearch.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winteryy.nbcsearch.domain.entity.StorageEntity
import com.winteryy.nbcsearch.domain.usecase.GetFavoriteItemMapUseCase
import com.winteryy.nbcsearch.domain.usecase.GetSearchImageUseCase
import com.winteryy.nbcsearch.domain.usecase.GetSearchVideoUseCase
import com.winteryy.nbcsearch.domain.usecase.InsertFavoriteItemUseCase
import com.winteryy.nbcsearch.domain.usecase.RemoveFavoriteItemUseCase
import com.winteryy.nbcsearch.presentation.common.ErrorEvent
import com.winteryy.nbcsearch.presentation.common.ErrorViewModel
import com.winteryy.nbcsearch.presentation.common.toErrorEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchImageUseCase: GetSearchImageUseCase,
    private val getSearchVideoUseCase: GetSearchVideoUseCase,
    private val getFavoriteItemMapUseCase: GetFavoriteItemMapUseCase,
    private val insertFavoriteItemUseCase: InsertFavoriteItemUseCase,
    private val removeFavoriteItemUseCase: RemoveFavoriteItemUseCase
) : ViewModel(), ErrorViewModel {

    private val _combinedSearchList = MutableStateFlow(SearchListUiState.init())
    val combinedSearchList: StateFlow<SearchListUiState> = _combinedSearchList.asStateFlow()

    private val _errorEvent = MutableSharedFlow<ErrorEvent>()
    override val errorEvent: SharedFlow<ErrorEvent> get() = _errorEvent

    private val searchItemFlow: MutableSharedFlow<List<SearchListItem>> = MutableSharedFlow()
    private val favoriteFlow: Flow<HashMap<String, StorageEntity>> = getFavoriteItemMapUseCase()

    private var pagingMeta: PagingMeta? = null
    private val pagingMetaMutex = Mutex()

    init {
        searchItemFlow.combine(favoriteFlow) { searchItemList, favoriteMap ->
            val combinedList = searchItemList.map {
                if (favoriteMap.containsKey(it.thumbnailUrl)) {
                    it.copy(isFavorite = true)
                } else {
                    it.copy(isFavorite = false)
                }
            }
            combinedList
        }.catch {
            _errorEvent.emit(it.toErrorEvent())
        }.onEach { combinedList ->
            _combinedSearchList.update { prev ->
                prev.copy(list = combinedList)
            }
        }.launchIn(viewModelScope)
    }

    fun searchListItem(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val searchImageDeferred = async { getSearchImageUseCase(query) }
                val searchVideoDeferred = async { getSearchVideoUseCase(query) }

                val searchImageResult = searchImageDeferred.await()
                val searchVideoResult = searchVideoDeferred.await()

                val integratedList =
                    (searchImageResult.documents.orEmpty() + searchVideoResult.documents.orEmpty())
                        .sortedByDescending { it.datetime }
                        .map { it. toListItem() }

                pagingMetaMutex.withLock {
                    pagingMeta = PagingMeta(
                        keyword = query,
                        imagePage = 1,
                        videoPage = 1,
                        imageIsEnd = searchImageResult.meta?.isEnd ?: true,
                        videoIsEnd = searchVideoResult.meta?.isEnd ?: true
                    )
                }

                searchItemFlow.emit(integratedList)

            }.onFailure {
                _errorEvent.emit(it.toErrorEvent())
            }
        }
    }

    fun loadMore() {
        pagingMeta?.let {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {

                    val searchImageDeferred = async {
                        if (it.imageIsEnd.not() && it.imagePage < 50) getSearchImageUseCase(
                            query = it.keyword,
                            page = it.imagePage + 1
                        )
                        else null
                    }

                    val searchVideoDeferred = async {
                        if (it.videoIsEnd.not() && it.imagePage < 15) getSearchVideoUseCase(
                            query = it.keyword,
                            page = it.videoPage + 1
                        )
                        else null
                    }

                    val searchImageResult = searchImageDeferred.await()
                    val searchVideoResult = searchVideoDeferred.await()

                    pagingMetaMutex.withLock {
                        var newPagingMeta = it.copy()
                        if (searchImageResult != null) {
                            newPagingMeta = newPagingMeta.copy(
                                imagePage = it.imagePage + 1,
                                imageIsEnd = searchImageResult.meta?.isEnd ?: true
                            )
                        }

                        if (searchVideoResult != null) {
                            newPagingMeta = newPagingMeta.copy(
                                videoPage = it.videoPage + 1,
                                videoIsEnd = searchVideoResult.meta?.isEnd ?: true
                            )
                        }
                        pagingMeta = newPagingMeta
                    }

                    val integratedList =
                        combinedSearchList.value.list +
                                (searchImageResult?.documents.orEmpty() + searchVideoResult?.documents.orEmpty())
                                .sortedByDescending { it.datetime }
                                .map { it.toListItem() }

                    searchItemFlow.emit(integratedList)

                }.onFailure {
                    _errorEvent.emit(it.toErrorEvent())
                }
            }
        }

    }

    fun saveToStorage(item: SearchListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                insertFavoriteItemUseCase(item.toStorageEntity())
            }.onFailure {
                _errorEvent.emit(it.toErrorEvent())
            }
        }
    }

    fun removeFromStorage(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                removeFavoriteItemUseCase(id)
            }.onFailure {
                _errorEvent.emit(it.toErrorEvent())
            }
        }
    }

}