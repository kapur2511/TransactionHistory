package com.cba.transactions.data.apimodels

import com.google.gson.annotations.SerializedName

data class AtmsItemApiModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val locationApiModel: LocationApiModel,
    @SerializedName("id")
    val id: String
)