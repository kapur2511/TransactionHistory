package com.cba.transactions.data.apimodels

import com.google.gson.annotations.SerializedName

data class TransactionsItemApiModel(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("isPending")
    val isPending: Boolean,
    @SerializedName("category")
    val category: String,
    @SerializedName("effectiveDate")
    val effectiveDate: String
)