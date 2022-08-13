package com.cba.transactions.domain.models

data class TransactionResponseModel(
    val listOfTransactions: List<TransactionModel>,
    val accountHeaderModel: AccountHeaderModel,
    val listOfAtms: List<AtmModel>?
)