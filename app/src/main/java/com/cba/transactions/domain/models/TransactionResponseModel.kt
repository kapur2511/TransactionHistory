package com.cba.transactions.domain.models

import com.cba.transactions.data.apimodels.AccountApiModel

data class TransactionResponseModel(
    val listOfTransactions: List<TransactionModel>,
    val accountModel: AccountApiModel,
    val listOfAtms: List<AtmModel>?,
    val pendingAmount: String
)