package com.cba.transactions.data.datasource

import com.cba.transactions.data.api.TransactionApi
import com.cba.transactions.data.apimodels.TransactionResponseApiModel
import com.cba.transactions.di.IoDispatcher
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.ResponseWrapper
import com.cba.transactions.util.SuccessResponseState
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRemoteDatasourceImpl @Inject constructor(
    private val transactionApi: TransactionApi,
    @IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher
): TransactionRemoteDatasource {

    override suspend fun getTransactions(): ResponseWrapper<TransactionResponseApiModel> {
        return withContext(ioCoroutineDispatcher) {
            val response = transactionApi.getTransactions()
            if (response.isSuccessful && response.body() != null) {
                SuccessResponseState(
                    data = response.body()!!
                )
            } else {
                val error = response.errorBody()
                ErrorResponseState(
                    throwable = Throwable(error?.toString())
                )
            }
        }
    }
}