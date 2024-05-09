package com.winteryy.nbcsearch.presentation.common

import kotlinx.coroutines.flow.SharedFlow

interface ErrorViewModel {
    val errorEvent: SharedFlow<ErrorEvent>
}