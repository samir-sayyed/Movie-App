package com.sam.movieapp.data.sources.service

import com.sam.movieapp.util.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url
        val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", API_KEY).build()
        request.url(url)
        return chain.proceed(request.build())
    }
}