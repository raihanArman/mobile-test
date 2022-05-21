package com.raydev.mobile_test.util

import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<ResponseState<ResultType>> = flow {
        emit(ResponseState.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(ResponseState.Loading())
            val apiResponse = createCall()
            apiResponse.collect {
                when (it) {
                    is ResponseState.Success -> {
                        saveCallResult(it.data!!)
                        emitAll(loadFromDB().map { ResponseState.Success(it) })
                    }
                    is ResponseState.Empty -> {
                        emitAll(loadFromDB().map { ResponseState.Success(it) })
                    }
                    is ResponseState.Error -> {
                        onFetchFailed()
                        emit(ResponseState.Error(it.errorMessage))
                    }
                }
            }
        } else {
            emitAll(loadFromDB().map { ResponseState.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ResponseState<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<ResponseState<ResultType>> = result

}