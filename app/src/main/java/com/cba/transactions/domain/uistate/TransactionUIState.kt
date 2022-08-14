package com.cba.transactions.domain.uistate

import com.cba.transactions.domain.models.AccountNameUIModel
import com.cba.transactions.domain.models.BaseModel

data class TransactionUIState(
    val accountName: AccountNameUIModel? = null,
    val list: List<BaseModel>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
)