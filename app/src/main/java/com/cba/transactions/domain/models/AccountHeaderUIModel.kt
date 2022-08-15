package com.cba.transactions.domain.models

import android.text.SpannableStringBuilder

data class AccountHeaderUIModel(
    val availableAmount: String,
    val balance: SpannableStringBuilder,
    val accountNumberAndBsb: SpannableStringBuilder,
    val pendingAmount: SpannableStringBuilder
): BaseModel