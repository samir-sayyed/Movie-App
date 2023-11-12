package com.sam.movieapp.di

import android.content.Context
import com.sam.movieapp.data.MovieRepository
import com.sam.movieapp.data.sources.service.MovieService
import com.sam.movieapp.data.sources.service.NetworkInterceptor
import com.sam.movieapp.data.sources.local.MovieDao
import com.sam.movieapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(NetworkInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(@ApplicationContext context: Context, movieService: MovieService, articleDao: MovieDao): MovieRepository {
        return MovieRepository(movieService, articleDao, context)
    }

}