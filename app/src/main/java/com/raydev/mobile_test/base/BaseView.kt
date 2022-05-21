package com.raydev.mobile_test.base

interface BaseView {
    fun onMessage(message: String?)
    fun onMessage(stringResId: Int)
    fun isNetworkConnect(): Boolean
}