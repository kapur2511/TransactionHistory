package com.cba.transactions.datasource

import com.cba.transactions.data.TransactionResponse
import com.cba.transactions.util.ResponseWrapper

interface TransactionRemoteDatasource {

    suspend fun getTransactions(): ResponseWrapper<TransactionResponse>
}