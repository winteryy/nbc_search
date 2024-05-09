package com.winteryy.nbcsearch.presentation.common

import com.winteryy.nbcsearch.domain.model.LocalError
import com.winteryy.nbcsearch.domain.model.NetworkError

/**
 * Presentation 레이어에서 사용되는 ErrorEvent
 */
data class ErrorEvent(
    val msg: String
) {
    companion object {
        val UNKNOWN_ERROR = ErrorEvent("알 수 없는 오류가 발생했습니다.")
    }
}

fun Throwable.toErrorEvent(): ErrorEvent {
    return when(this) {
        is NetworkError -> ErrorEvent(msg)
        is LocalError -> ErrorEvent(msg)
        else -> ErrorEvent.UNKNOWN_ERROR
    }
}