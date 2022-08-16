package com.cba.transactions.domain.models

import android.text.SpannableStringBuilder

data class DateUIModel(
    val date: SpannableStringBuilder
): BaseModel {

    override fun equals(other: Any?): Boolean {
        return other is DateUIModel &&
                other.date.toString() == date.toString()
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}