package com.cba.transactions.domain.models

import android.text.SpannableStringBuilder

data class TransactionUIModel(
    val imageSrc: Int,
    val description: SpannableStringBuilder,
    val amount: String
): BaseModel