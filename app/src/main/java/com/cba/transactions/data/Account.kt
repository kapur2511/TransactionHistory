package com.cba.transactions.data

import com.google.gson.annotations.SerializedName

data class Account(
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