package com.cba.transactions.data

import com.google.gson.annotations.SerializedName

data class AtmsItem(
    @SerializedName("address")
    val address: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("id")
    val id: String
)