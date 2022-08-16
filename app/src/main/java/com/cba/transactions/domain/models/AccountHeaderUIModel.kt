package com.cba.transactions.domain.models

import android.text.SpannableStringBuilder

data class AccountHeaderUIModel(
    val availableAmount: String,
    val balance: SpannableStringBuilder,
    val accountNumberAndBsb: SpannableStringBuilder,
    val pendingAmount: SpannableStringBuilder
): BaseModel {

    override fun equals(other: Any?): Boolean {
        return other is AccountHeaderUIModel &&
        other.availableAmount == availableAmount &&
        other.pendingAmount.toString() == pendingAmount.toString() &&
        other.accountNumberAndBsb.toString() == accountNumberAndBsb.toString()
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}