package com.cba.transactions.domain.uistate

import com.cba.transactions.domain.models.AccountNameUIModel
import com.cba.transactions.domain.models.BaseModel

data class TransactionUIState(
    val accountName: AccountNameUIModel? = null,
    val list: List<BaseModel>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return other is TransactionUIState &&
                other.isLoading == isLoading &&
                other.accountName == accountName &&
                other.list?.size == list?.size &&
                other.error?.message == error?.message
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}