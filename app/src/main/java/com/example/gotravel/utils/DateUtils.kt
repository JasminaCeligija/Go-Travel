package com.example.gotravel.utils

import java.text.SimpleDateFormat
import java.util.*


 fun convertDate(dateInMilliseconds: Long): String? {
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())
    return  simpleDateFormat.format(dateInMilliseconds)
}