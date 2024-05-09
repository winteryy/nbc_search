package com.winteryy.nbcsearch.presentation.common

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.snackbar.Snackbar

/**
 * 노출되는 소프트 키보드를 숨기는 확장 함수
 */
fun View.hideSoftKeyboard() {
    val manager = getSystemService(context, InputMethodManager::class.java)
    manager?.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * 에러 메세지를 String으로 받아 바로 SnackBar로 노출하는 확장 함수
 */
fun View.showErrorSnackBar(errorText: String) {
    Snackbar.make(this, errorText, Snackbar.LENGTH_SHORT).show()
}