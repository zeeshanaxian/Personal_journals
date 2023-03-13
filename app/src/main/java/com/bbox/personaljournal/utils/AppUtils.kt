package com.bbox.personaljournal.utils

import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun getMonthName(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val formattedMonth = dateFormat.format(currentTime)
        return formattedMonth.uppercase()
    }

    fun getCurrentTime(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedMonth = dateFormat.format(currentTime)
        return formattedMonth
    }

    fun getDayAndDate(): String {
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val formattedDate = dateFormat.format(currentTime)

        val calendar = Calendar.getInstance()
        val dayOfWeek =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
        val dayPrefix = dayOfWeek?.substring(0, 3)
        return "$dayPrefix, $formattedDate"
    }
}