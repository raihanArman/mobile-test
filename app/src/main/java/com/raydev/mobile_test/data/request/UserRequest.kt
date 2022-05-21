package com.raydev.mobile_test.data.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("city")
    var city: String,

)