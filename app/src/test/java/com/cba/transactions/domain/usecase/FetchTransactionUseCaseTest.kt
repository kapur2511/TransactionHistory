package com.cba.transactions.domain.usecase

import com.cba.transactions.domain.uistate.TransactionUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchTransactionUseCaseTest {

    private lateinit var fetchTransactionUseCase: FetchTransactionUseCase
    private lateinit var repository: FakeTransactionRepository

    @Before
    fun setUp() {
        repository = FakeTransactionRepository()
        fetchTransactionUseCase = FetchTransactionUseCase(
            repository,
            Dispatchers.IO
        )
    }

    @Test
    fun `when transaction api fails then error should be thrown`() {
        repository.shouldSucceed = false
        runBlocking {
            val result = fetchTransactionUseCase()
            val expectedResult = TransactionUIState(
                isLoading = false,
                list = null,
                accountName = null,
                error = Throwable("Something went wrong")
            )
            assertEquals(expectedResult, result)
        }
    }

}