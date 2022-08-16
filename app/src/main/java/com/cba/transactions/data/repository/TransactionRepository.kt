package com.cba.transactions.data.repository

import com.cba.transactions.domain.models.TransactionModel
import com.cba.transactions.domain.models.TransactionResponseModel
import com.cba.transactions.util.ResponseWrapper

interface TransactionRepository {

    suspend fun getTransactions(): ResponseWrapper<TransactionResponseModel>

    suspend fun getTransactionModel(transactionId: String): TransactionModel?
}