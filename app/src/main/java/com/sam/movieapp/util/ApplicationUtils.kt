package com.sam.movieapp.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object ApplicationUtils {

    fun getRelativeTime(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val date = dateFormat.parse(dateString)
            val currentTime = System.currentTimeMillis()
            val timeDifference = currentTime - date.time
            val seconds = timeDifference / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return when {
                days >= 2 -> "$days days ago"
                days == 1L -> "yesterday"
                hours >= 1 -> "$hours hours ago"
                else -> "$minutes minutes ago"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

}