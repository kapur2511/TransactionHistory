package com.cba.transactions.domain.models

data class TransactionResponseModel(
    val listOfTransactions: List<TransactionModel>,
    val accountHeaderUIModel: AccountHeaderUIModel,
    val listOfAtms: List<AtmModel>?,
    val accountNameUIModel: AccountNameUIModel
)