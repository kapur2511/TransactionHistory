package com.cba.transactions.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionModel(
    val isPending: Boolean,
    val amount: String,
    val transactionId: String,
    val description: String,
    val category: String,
    val effectiveDate: String
): Parcelable