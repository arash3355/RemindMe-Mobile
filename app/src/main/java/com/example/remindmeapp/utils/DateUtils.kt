package com.example.remindmeapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private val formatter = SimpleDateFormat(
        "d/M/yyyy",
        Locale.getDefault()
    )

    fun today(): String {

        return formatter.format(Date())

    }

    fun format(timeInMillis: Long): String {

        return formatter.format(
            Date(timeInMillis)
        )

    }

}