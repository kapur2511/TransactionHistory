package com.cba.transactions.domain.models

import android.text.SpannableStringBuilder

data class TransactionUIModel(
    val transactionId: String,
    val imageSrc: Int,
    val description: SpannableStringBuilder,
    val amount: String
): BaseModel {

    override fun equals(other: Any?): Boolean {
        return other is TransactionUIModel &&
                other.imageSrc == imageSrc &&
                other.description.toString() == description.toString() &&
                other.amount == amount &&
                other.transactionId == transactionId
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}