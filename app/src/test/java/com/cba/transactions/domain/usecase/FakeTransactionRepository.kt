package com.cba.transactions.domain.usecase

import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.domain.models.TransactionResponseModel
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.ResponseWrapper
import com.cba.transactions.util.SuccessResponseState

class FakeTransactionRepository: TransactionRepository {
    // use this to toggle success and failure
    var shouldSucceed = false

    override suspend fun getTransactions(): ResponseWrapper<TransactionResponseModel> {
        return if (shouldSucceed) {
            SuccessResponseState(
                data = TransactionStub.getSuccessTransactionResponse()
            )
        } else {
            ErrorResponseState(
                throwable = Throwable("Something went wrong")
            )
        }
    }
}