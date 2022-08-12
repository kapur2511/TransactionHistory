package com.cba.transactions.data.apimodels

import com.google.gson.annotations.SerializedName

data class AccountApiModel(
    @SerializedName("bsb")
    val bsb: String,
    @SerializedName("balance")
    val balance: String,
    @SerializedName("accountName")
    val accountName: String,
    @SerializedName("available")
    val available: String,
    @SerializedName("accountNumber")
    val accountNumber: String
)