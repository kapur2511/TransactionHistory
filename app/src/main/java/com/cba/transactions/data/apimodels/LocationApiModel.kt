package com.cba.transactions.data.apimodels

import com.google.gson.annotations.SerializedName

data class LocationApiModel(
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)