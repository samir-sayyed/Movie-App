package com.sam.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.sam.movieapp.R
import com.sam.movieapp.util.MOVIE_WORKER
import com.sam.movieapp.util.StorageUtil
import com.sam.movieapp.worker.MovieWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val isDarkTheme = StorageUtil.getInstance(this).isDarkTheme()
        if (isDarkTheme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPeriodicApiCall()
    }

    private fun initPeriodicApiCall() {
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            MovieWorker::class.java,
            30,
            TimeUnit.MINUTES
        )
            .setInitialDelay(5, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag(MOVIE_WORKER)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                MOVIE_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }

    fun switchTheme() {
        var isDarkTheme = StorageUtil.getInstance(this).isDarkTheme()
        if (isDarkTheme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        isDarkTheme = !isDarkTheme
        StorageUtil.getInstance(this).setDarkTheme(isDarkTheme)
    }
}