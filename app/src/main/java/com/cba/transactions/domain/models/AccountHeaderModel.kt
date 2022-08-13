package com.cba.transactions.domain.models

data class AccountHeaderModel(
    val availableAmount: String,
    val balance: String,
    val bsb: String,
    val accountNumber: String,
    val accountName: String,
    val pendingAmount: String
): BaseModel