package com.cba.transactions.data.repository

import com.cba.transactions.data.datasource.TransactionRemoteDatasource
import com.cba.transactions.di.IoDispatcher
import com.cba.transactions.domain.models.AtmModel
import com.cba.transactions.domain.models.LatLng
import com.cba.transactions.domain.models.TransactionModel
import com.cba.transactions.domain.models.TransactionResponseModel
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.ResponseWrapper
import com.cba.transactions.util.SuccessResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionRemoteDatasource: TransactionRemoteDatasource,
    @IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher
): TransactionRepository {

    private var inMemoryCache: TransactionResponseModel? = null

    override suspend fun getTransactions(): ResponseWrapper<TransactionResponseModel> {
        return withContext(ioCoroutineDispatcher) {
            when (val response = transactionRemoteDatasource.getTransactions()) {
                is SuccessResponseState -> {
                    with(response.data) {
                        var totalPendingAmount = 0F

                        val listOfTransactions = this.transactions.map { transactionApiModel ->
                            if (transactionApiModel.isPending) {
                                totalPendingAmount += transactionApiModel.amount.toFloat()
                            }
                            TransactionModel(
                                isPending = transactionApiModel.isPending,
                                transactionId = transactionApiModel.id,
                                amount = transactionApiModel.amount,
                                category = transactionApiModel.category,
                                effectiveDate = transactionApiModel.effectiveDate,
                                description = transactionApiModel.description
                            )
                        }
                        val listOfAtms = this.atms?.map { atmsItemApiModel ->
                            AtmModel(
                                atmId = atmsItemApiModel.id,
                                address = atmsItemApiModel.address,
                                location = LatLng(
                                    lat = atmsItemApiModel.locationApiModel.lat,
                                    lng = atmsItemApiModel.locationApiModel.lon
                                ),
                                name = atmsItemApiModel.name
                            )
                        }
                        val transactionResponseModel = TransactionResponseModel(
                            listOfTransactions = listOfTransactions,
                            listOfAtms = listOfAtms,
                            accountModel = accountApiModel,
                            pendingAmount = totalPendingAmount.toString()
                        )
                        inMemoryCache = transactionResponseModel
                        SuccessResponseState(
                            data = transactionResponseModel
                        )
                    }
                }
                is ErrorResponseState -> {
                    ErrorResponseState(
                        throwable = response.throwable
                    )
                }
            }
        }
    }

    override suspend fun getTransactionModel(transactionId: String): TransactionModel? {
        return inMemoryCache?.listOfTransactions?.find {
            it.transactionId == transactionId
        }
    }
}