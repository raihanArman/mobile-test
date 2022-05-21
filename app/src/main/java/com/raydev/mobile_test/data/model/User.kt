package com.raydev.mobile_test.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    var name: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("city")
    var city: String,

    @SerializedName("id")
    var id: String,

)