package com.cba.transactions.domain.models

data class AccountHeaderUIModel(
    val availableAmount: String,
    val balance: String,
    val bsb: String,
    val accountNumber: String,
    val pendingAmount: String
): BaseModel