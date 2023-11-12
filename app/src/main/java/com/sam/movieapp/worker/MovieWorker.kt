package com.sam.movieapp.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.sam.movieapp.MovieApplication
import com.sam.movieapp.R
import com.sam.movieapp.data.MovieRepository
import com.sam.movieapp.data.sources.service.MovieService
import com.sam.movieapp.data.sources.local.MovieDao
import com.sam.movieapp.di.IoDispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@HiltWorker
class MovieWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val movieRepository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            try {
                if (isAppInForeground())
                    movieRepository.getMovies()
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }

    private fun isAppInForeground(): Boolean {
        return (applicationContext as MovieApplication).isAppInForeground()
    }

}