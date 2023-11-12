package com.sam.movieapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication: Application() {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private var foregroundActivityCount = 0


    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(hiltWorkerFactory).build()
        )
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

            override fun onActivityStarted(activity: Activity) {
                foregroundActivityCount++
            }
            override fun onActivityResumed(activity: Activity) = Unit

            override fun onActivityPaused(activity: Activity) = Unit

            override fun onActivityStopped(activity: Activity) {
                foregroundActivityCount--
            }
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) =
                Unit

            override fun onActivityDestroyed(activity: Activity) = Unit
        })
    }

    fun isAppInForeground(): Boolean {
        return foregroundActivityCount > 0
    }

}