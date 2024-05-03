package com.winteryy.nbcsearch.presentation.search

import android.util.Log
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

    fun getLogImageItem(query: String) = viewModelScope.launch {
        val result = getSearchImageUseCase(query)
        Log.d("SearchViewModel", result.documents.toString())
    }
}