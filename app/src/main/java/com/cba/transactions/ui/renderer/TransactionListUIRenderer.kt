package com.cba.transactions.ui.renderer

import com.cba.transactions.domain.uistate.TransactionUIState

class TransactionListUIRenderer(
    private val transactionView: TransactionView
) {

    fun render(transactionUIState: TransactionUIState) {
        when {
            transactionUIState.isLoading -> {
                transactionView.showLoading()
            }
            transactionUIState.accountName != null
                    && transactionUIState.list.isNullOrEmpty().not() -> {
                transactionView.showSuccess(
                    accountNameUIModel = transactionUIState.accountName,
                    list = transactionUIState.list!!
                )
            }
            transactionUIState.error != null -> {
                transactionView.showError(error = transactionUIState.error)
            }
        }
    }
}