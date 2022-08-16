package com.cba.transactions.domain.usecase

import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.di.IoDispatcher
import com.cba.transactions.domain.models.TransactionModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionDetailsUseCase@Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        transactionId: String
    ): TransactionModel? = withContext(ioDispatcher) {
        transactionRepository.getTransactionModel(transactionId = transactionId)
    }
}