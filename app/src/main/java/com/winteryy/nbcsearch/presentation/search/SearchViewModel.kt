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

    //View에 노출하기 위한 리스트가 담긴 StateFlow
    private val _combinedSearchList = MutableStateFlow(SearchListUiState.init())
    val combinedSearchList: StateFlow<SearchListUiState> = _combinedSearchList.asStateFlow()

    //에러 발생 시, View에서 감지할 SharedFlow
    private val _errorEvent = MutableSharedFlow<ErrorEvent>()
    override val errorEvent: SharedFlow<ErrorEvent> get() = _errorEvent

    //검색 할 때마다 적절히 변환된 결과가 방출될 SharedFlow 및 로컬 데이터로부터 지속적으로 받는 Flow
    private val searchItemFlow: MutableSharedFlow<List<SearchListItem>> = MutableSharedFlow()
    private val favoriteFlow: Flow<HashMap<String, StorageEntity>> = getFavoriteItemMapUseCase()

    //페이징 처리용
    private var pagingMeta: PagingMeta? = null
    private val pagingMetaMutex = Mutex()

    init {
        searchItemFlow.combine(favoriteFlow) { searchItemList, favoriteMap ->
            val combinedList = searchItemList.map {
                //검색 결과로 받아온 아이템이 보관함에 있는지 확인
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

    /**
     * 리스트에 노출할 아이템들을 검색하는 메소드
     *
     * @param query 검색어
     */
    fun searchListItem(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                //각각의 API를 병렬로 받아올 수 있도록
                val searchImageDeferred = async { getSearchImageUseCase(query) }
                val searchVideoDeferred = async { getSearchVideoUseCase(query) }

                //모든 결과 받고 나서 진행
                val searchImageResult = searchImageDeferred.await()
                val searchVideoResult = searchVideoDeferred.await()

                //두 결과 합쳐서 시간순 정렬 후, View에서 사용하는 타입으로 변환
                val integratedList =
                    (searchImageResult.documents.orEmpty() + searchVideoResult.documents.orEmpty())
                        .sortedByDescending { it.datetime }
                        .map { it.toListItem() }

                //race condition 방지하면서 pagingMeta에 저장
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

    /**
     * 페이징 정보를 이용해 다음 페이지를 불러오는 메소드
     */
    fun loadMore() {
        //pagingMeta null 이라면 검색 이력이 없는데, loadMore()이 호출되는 이상한 경우
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
                            //이미지 정상적으로 받아왔다면 페이징 데이터에 반영
                            newPagingMeta = newPagingMeta.copy(
                                imagePage = it.imagePage + 1,
                                imageIsEnd = searchImageResult.meta?.isEnd ?: true
                            )
                        }

                        if (searchVideoResult != null) {
                            //비디오 정상적으로 받아왔다면 페이징 데이터에 반영
                            newPagingMeta = newPagingMeta.copy(
                                videoPage = it.videoPage + 1,
                                videoIsEnd = searchVideoResult.meta?.isEnd ?: true
                            )
                        }
                        pagingMeta = newPagingMeta
                    }

                    /*
                    기존에 노출 중이던 데이터에 새로 불러온 데이터들까지 더해서 갱신
                    이 과제의 로직 상, 이미지와 비디오의 완전 무결한 시간순 정렬이 보장될 수 없기 때문에
                    기존 노출 데이터와 합쳐서 정렬하는 것이 아닌, 추가되는 데이터를 정렬해서 그대로 붙이는 형태로 구현
                    */
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

    /**
     * 보관함에 아이템을 저장하기 위한 메소드
     */
    fun saveToStorage(item: SearchListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                insertFavoriteItemUseCase(item.toStorageEntity())
            }.onFailure {
                _errorEvent.emit(it.toErrorEvent())
            }
        }
    }

    /**
     * 보관함에 저장된 아이템을 삭제하는 메소드
     */
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