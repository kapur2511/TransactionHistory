package com.cba.transactions.data.datasource

import com.cba.transactions.data.apimodels.TransactionResponseApiModel
import com.cba.transactions.util.ResponseWrapper

interface TransactionRemoteDatasource {

    suspend fun getTransactions(): ResponseWrapper<TransactionResponseApiModel>
}