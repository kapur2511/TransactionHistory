package com.cba.transactions.datasource

import com.cba.transactions.data.TransactionResponse
import com.cba.transactions.util.ResponseWrapper

class TransactionRemoteDatasourceImpl: TransactionRemoteDatasource {

    override suspend fun getTransactions(): ResponseWrapper<TransactionResponse> {
        TODO("Not yet implemented")
    }
}