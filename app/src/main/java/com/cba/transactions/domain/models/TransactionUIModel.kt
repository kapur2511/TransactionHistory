package com.cba.transactions.domain.models

data class TransactionUIModel(
    val imageSrc: Int,
    val description: String,
    val amount: String
): BaseModel