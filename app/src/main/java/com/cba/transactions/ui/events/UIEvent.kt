package com.cba.transactions.ui.events

import com.cba.transactions.domain.models.TransactionModel

sealed class UIEvent

data class TransactionDetailsEvent(
    val model: TransactionModel
): UIEvent()

data class ToastEvent(
    val message: String
): UIEvent()
// can add more events here for future expansion