package com.cba.transactions.data

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)