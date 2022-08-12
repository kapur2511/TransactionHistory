package com.cba.transactions.data.datasource

import com.cba.transactions.data.apimodels.TransactionResponseApiModel
import com.cba.transactions.util.ResponseWrapper

class TransactionRemoteDatasourceImpl: TransactionRemoteDatasource {

    override suspend fun getTransactions(): ResponseWrapper<TransactionResponseApiModel> {
        TODO("Not yet implemented")
    }
}