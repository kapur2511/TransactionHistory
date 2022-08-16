package com.cba.transactions.domain.usecase

import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.domain.models.TransactionModel
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

    override suspend fun getTransactionModel(transactionId: String): TransactionModel? {
        return if (shouldSucceed) {
            TransactionModel(
                isPending = true,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-28"
            )
        } else {
            null
        }
    }
}