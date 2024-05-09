package com.winteryy.nbcsearch.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date?.toUiString(): String {
    val dateFormat = "yyyy-MM-dd HH:mm:ss"
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.KOREA)

    return if(this==null) "" else simpleDateFormat.format(this)
}