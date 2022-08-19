package com.dttcaltekin.cryptoapp.base

import com.dttcaltekin.cryptoapp.model.errorResponse.ErrorResponse
import com.dttcaltekin.cryptoapp.utils.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiRequest(
        apiRequest: suspend () -> T) : NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiRequest.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        NetworkResult.Error(false, errorBodyParser(throwable.response()?.errorBody()?.toString()))
                    }
                    else -> NetworkResult.Error(true, throwable.localizedMessage)
                }
            }
        }
    }

}

private fun errorBodyParser(error: String?) : String {
    error?.let {
        return try {
            val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
            val errorMessage = errorResponse.status.error_message
            errorMessage ?: "An unexpected error has occurred"
        } catch (e: Exception) {
            "An unexpected error has occurred"
        }
    }

    return "An unexpected error has occurred"
}