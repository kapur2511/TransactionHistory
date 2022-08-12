package com.cba.transactions.data

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("atms")
    val atms: List<AtmsItem>?,
    @SerializedName("transactions")
    val transactions: List<TransactionsItem>?,
    @SerializedName("account")
    val account: Account
)