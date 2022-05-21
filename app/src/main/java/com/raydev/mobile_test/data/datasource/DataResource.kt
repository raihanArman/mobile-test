package com.raydev.mobile_test.data.datasource

import com.raydev.mobile_test.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class DataResource {
    protected fun <T> getResult(call: suspend () -> Response<T>): Flow<ResponseState<T>> = flow {
        emit(ResponseState.Loading())
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) emit(ResponseState.Success(body))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.toString()))
        }
    }

    protected suspend fun <T> getResultWithoutState(call: suspend () -> Response<T>): T? {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return body
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

    private fun <T> error(message: String): ResponseState<T> {
        return ResponseState.Error("Network call has failed for a following reason: $message")
    }
}