package com.sam.movieapp.util

import okhttp3.ResponseBody

sealed class ApiResult<T>(
    data: T? = null,
    message: String? = null,
    errorBody: ResponseBody? = null
) {

    class Success<T>(val data: T? = null) : ApiResult<T>(data)

    class Error<T>(val data: ResponseBody? = null, val message: String? = null) :
        ApiResult<T>(errorBody = data, message = message)

    class Loading<T>() : ApiResult<T>()

}