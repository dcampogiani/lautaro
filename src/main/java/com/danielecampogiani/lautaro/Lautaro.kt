package com.danielecampogiani.lautaro

import com.danielecampogiani.lautaro.internal.Api
import com.danielecampogiani.lautaro.internal.map
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response

object Lautaro {

    private val api: Api by lazy { Api() }

    suspend fun getEvents(): Result<List<Event>> = fetchFromNetwork(
        api.getTop()
    ) {
        map(it)
    }

    suspend fun getDetail(url: String): Result<List<Source>> = fetchFromNetwork(
        api.getDetails("$url.json")
    ) {
        map(it.orEmpty())
    }

    private suspend fun <T, R> fetchFromNetwork(
        deferred: Deferred<Response<T>>,
        mappingFunction: (T?) -> R
    ): Result<R> {
        return try {
            val response = deferred.await()
            if (response.isSuccessful) {
                val body = response.body()
                val result = mappingFunction(body)
                Result.Success(result)
            } else {
                val errorBody = response.errorBody()?.toString()
                Result.Error(errorBody)
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    sealed class Result<T> {
        data class Success<T>(val value: T) : Result<T>()
        data class Error<T>(val value: String?) : Result<T>()
        data class Failure<T>(val value: Throwable) : Result<T>()
    }
}