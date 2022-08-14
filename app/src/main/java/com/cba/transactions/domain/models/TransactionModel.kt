package com.cba.transactions.domain.models

data class TransactionModel(
    val isPending: Boolean,
    val amount: String,
    val transactionId: String,
    val description: String,
    val category: String,
    val effectiveDate: String
)