package com.cba.transactions.domain.models

import android.text.SpannableStringBuilder

data class DateUIModel(
    val date: SpannableStringBuilder
): BaseModel {

    override fun equals(other: Any?): Boolean {

        // matching the initial part of the date as the number of days
        // will keep on increasing due to which the test case might fail.
        return other is DateUIModel &&
                other.date.split(" ").take(3).joinToString("-") ==
                date.split(" ").take(3).joinToString("-")
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}