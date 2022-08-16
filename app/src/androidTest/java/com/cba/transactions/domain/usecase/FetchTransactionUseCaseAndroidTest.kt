package com.cba.transactions.domain.usecase

import com.cba.transactions.domain.models.AccountHeaderUIModel
import com.cba.transactions.domain.models.DateUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class FetchTransactionUseCaseAndroidTest {

    private lateinit var fetchTransactionUseCase: FetchTransactionUseCase
    private lateinit var repositoryInstrumentation: FakeInstrumentationTransactionRepository

    @Before
    fun setUp() {
        repositoryInstrumentation = FakeInstrumentationTransactionRepository()
        fetchTransactionUseCase = FetchTransactionUseCase(
            repositoryInstrumentation,
            Dispatchers.IO
        )
    }

    @Test
    fun when_transaction_api_succeeds_then_transaction_list_and_account_name_should_not_be_null() {
        repositoryInstrumentation.shouldSucceed = true
        runBlocking {
            val result = fetchTransactionUseCase()
            assertTrue(result.list.isNullOrEmpty().not())
            assertTrue(result.accountName != null)
        }
    }

    @Test
    fun when_transaction_api_fails_then_transaction_list_and_account_name_should_not_be_null() {
        repositoryInstrumentation.shouldSucceed = true
        runBlocking {
            val result = fetchTransactionUseCase()
            assertTrue(result.list.isNullOrEmpty().not())
            assertTrue(result.accountName != null)
        }
    }

    @Test
    fun when_transaction_api_succeeds_then_transaction_ui_state_should_have_data() {
        repositoryInstrumentation.shouldSucceed = true
        runBlocking {
            val result = fetchTransactionUseCase()
            val expectedResult = InstrumentationTransactionStub.getSuccessTransactionUIState()
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun when_transaction_api_succeeds_then_pending_amount_should_be_correct() {
        repositoryInstrumentation.shouldSucceed = true
        runBlocking {
            val result = fetchTransactionUseCase()

            val expectedResult = (InstrumentationTransactionStub.getSuccessTransactionUIState()
                .list?.get(0) as AccountHeaderUIModel).pendingAmount.toString()

            val actualPendingAmount = result.list?.find {
                it is AccountHeaderUIModel
            }?.let {
                (it as AccountHeaderUIModel).pendingAmount.toString()
            }

            assertEquals(expectedResult, actualPendingAmount)
        }
    }

    @Test
    fun when_transaction_api_succeeds_then_account_name_is_not_null() {
        repositoryInstrumentation.shouldSucceed = true
        runBlocking {
            val response = fetchTransactionUseCase()
            val actualResult = response.accountName
            val expectedResult = InstrumentationTransactionStub.getSuccessTransactionUIState().accountName
            assertNotNull(actualResult)
            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun when_transaction_api_succeeds_verify_that_ui_models_are_in_order() {
        repositoryInstrumentation.shouldSucceed = true
        repositoryInstrumentation.shuffleTransaction = true
        runBlocking {
            val response = fetchTransactionUseCase()
            val expectedResult = InstrumentationTransactionStub.getSuccessTransactionUIState()
            response.list?.forEachIndexed { index, actualInstance ->
                val expectedInstance = expectedResult.list?.get(index)
                assertEquals(expectedInstance, actualInstance)
            }
        }
    }

    @Test
    fun when_transaction_api_succeeds_verify_that_transaction_are_sorted() {
        repositoryInstrumentation.shouldSucceed = true
        repositoryInstrumentation.shuffleTransaction = true
        runBlocking {
            val response = fetchTransactionUseCase()
            val list = response.list!!.filterIsInstance<DateUIModel>()
            val sdf = SimpleDateFormat("EEE-dd-MMM", Locale.getDefault())

            for (i in 0..list.size - 2) {
                val actualDateString = list[i].date.split(" ").take(3).joinToString("-")
                val actualDate = sdf.parse(actualDateString)

                val nextActualDateString = list[i+1].date.split(" ").take(3).joinToString("-")
                val nextActualDate = sdf.parse(nextActualDateString)
                assertTrue(actualDate!!.time > nextActualDate!!.time)
            }
        }
    }
}