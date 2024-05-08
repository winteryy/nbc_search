package com.winteryy.nbcsearch.presentation.common

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.snackbar.Snackbar

fun View.hideSoftKeyboard() {
    val manager = getSystemService(context, InputMethodManager::class.java)
    manager?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showErrorSnackBar(errorText: String) {
    Snackbar.make(this, errorText, Snackbar.LENGTH_SHORT).show()
}