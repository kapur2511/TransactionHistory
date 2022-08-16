package com.cba.transactions.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class TransactionDetailsUseCaseTest {

    private lateinit var transactionDetailsUseCase: TransactionDetailsUseCase
    private lateinit var repositoryInstrumentation: FakeInstrumentationTransactionRepository

    @Before
    fun setUp() {
        repositoryInstrumentation = FakeInstrumentationTransactionRepository()
        transactionDetailsUseCase = TransactionDetailsUseCase(
            repositoryInstrumentation,
            Dispatchers.IO
        )
    }

    @Test
    fun when_user_clicks_on_transaction_and_model_is_available() {
        repositoryInstrumentation.shouldSucceed = true
        runBlocking {
            val result = transactionDetailsUseCase("quyiuy11")
            assertTrue(result != null)
            assertTrue(result?.transactionId == "quyiuy11")
        }
    }

    @Test
    fun when_user_clicks_on_transaction_and_model_is_not_available() {
        repositoryInstrumentation.shouldSucceed = false
        runBlocking {
            val result = transactionDetailsUseCase("quyiuy11")
            assertTrue(result == null)
        }
    }
}