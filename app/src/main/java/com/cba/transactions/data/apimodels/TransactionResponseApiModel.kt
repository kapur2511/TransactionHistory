package com.cba.transactions.data.apimodels

import com.google.gson.annotations.SerializedName

data class TransactionResponseApiModel(
    @SerializedName("atms")
    val atms: List<AtmsItemApiModel>?,
    @SerializedName("transactions")
    val transactions: List<TransactionsItemApiModel>,
    @SerializedName("account")
    val accountApiModel: AccountApiModel
)