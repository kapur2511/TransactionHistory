package com.cba.transactions.domain.usecase

import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.domain.uistate.TransactionUIState
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.SuccessResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        when (val result = transactionRepository.getTransactions()) {
            is SuccessResponseState -> {
                TransactionUIState(
                    list = listOf(),
                    accountName = result.data.accountHeaderModel.accountName,
                    isLoading = false,
                    error = null
                )
            }
            is ErrorResponseState -> {
                TransactionUIState(
                    list = null,
                    accountName = null,
                    isLoading = false,
                    error = result.throwable
                )
            }
        }
    }

}