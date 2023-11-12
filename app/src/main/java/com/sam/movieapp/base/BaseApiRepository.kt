package com.sam.movieapp.base

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.sam.movieapp.util.NO_INTERNET_CONNECTION
import com.sam.movieapp.util.ApiResult
import com.sam.movieapp.util.ERROR
import com.sam.movieapp.util.MESSAGE
import com.sam.movieapp.util.NetworkStatusHelper
import com.sam.movieapp.util.SOMETHING_WENT_WRONG
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

abstract class BaseApiRepository(private val context: Context) {

    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> {
        return if (NetworkStatusHelper.isOnline(context)) {
            try {
                val response = apiCall()
                if (response.isSuccessful && response.body() != null) {
                    ApiResult.Success(response.body())
                } else {
                    ApiResult.Error(response.errorBody(), getErrorMessage(response.errorBody()))
                }
            } catch (e: Exception) {
                Log.e("safeApiCall", "safeApiCall: ${e.message}", e )
                ApiResult.Error(null, e.message)
            }
        } else {
            Log.e("safeApiCall", "safeApiCall: $NO_INTERNET_CONNECTION",  )
            ApiResult.Error(null, NO_INTERNET_CONNECTION)
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody.toString())
            when {
                jsonObject.has(MESSAGE) -> jsonObject.getString(MESSAGE)
                jsonObject.has(ERROR) -> jsonObject.getString(ERROR)
                else -> SOMETHING_WENT_WRONG
            }
        }catch (e: Exception){
            Log.e("getErrorMessage", "getErrorMessage: ${e.message}", e )
            SOMETHING_WENT_WRONG
        }
    }
}