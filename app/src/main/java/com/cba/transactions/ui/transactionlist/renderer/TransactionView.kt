package com.cba.transactions.ui.transactionlist.renderer

import com.cba.transactions.domain.models.AccountNameUIModel
import com.cba.transactions.domain.models.BaseModel

interface TransactionView {

    fun showLoading()
    fun showSuccess(accountNameUIModel: AccountNameUIModel, list: List<BaseModel>)
    fun showError(error: Throwable?)
}