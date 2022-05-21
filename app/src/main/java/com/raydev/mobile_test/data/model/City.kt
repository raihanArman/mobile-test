package com.raydev.mobile_test.data.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("name")
    var name: String,

    @SerializedName("id")
    var id: String,
)