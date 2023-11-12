package com.sam.movieapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class StorageUtil private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        AppCompatActivity.MODE_PRIVATE
    )

    fun isDarkTheme(): Boolean {
        return prefs.getBoolean(THEME_KEY, false)
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        prefs.edit().putBoolean(THEME_KEY, isDarkTheme).apply()
    }
    
    companion object{
        private const val PREFS_NAME = "MyPrefsFile"
        private const val THEME_KEY = "isDarkTheme"
        private var sInstance = WeakReference<StorageUtil?>(null)
        fun getInstance(context: Context): StorageUtil {
            var config = sInstance.get()
            if (config == null) {
                config = StorageUtil(context.applicationContext)
                sInstance = WeakReference(config)
            }
            return config
        }
    }
    
}