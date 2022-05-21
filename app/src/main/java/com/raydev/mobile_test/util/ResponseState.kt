package com.raydev.mobile_test.util

sealed class ResponseState <out T>(
    val data: T?= null,
    val message: String ?= null
){
    data class Success<T>(val success: T?): ResponseState<T>(success)
    data class  Loading<out T>(val success: T? = null): ResponseState<T>()
    data class Error(val errorMessage: String): ResponseState<Nothing>()
    object Empty : ResponseState<Nothing>()
}