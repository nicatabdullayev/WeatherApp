package com.example.weatherappfinalproject.util

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {
    fun getReadableDateFromLong(dateInMillis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/YYYY")
        return formatter.format(Date(dateInMillis * 1000))
    }
}
