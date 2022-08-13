package com.cba.transactions.data.repository

import com.cba.transactions.data.datasource.TransactionRemoteDatasource
import com.cba.transactions.domain.models.*
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.ResponseWrapper
import com.cba.transactions.util.SuccessResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionRemoteDatasource: TransactionRemoteDatasource,
    private val ioCoroutineDispatcher: CoroutineDispatcher
): TransactionRepository {

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
                        val accountHeaderModel = with(this.accountApiModel) {
                            AccountHeaderModel(
                                availableAmount = available,
                                accountName = accountName,
                                accountNumber = accountNumber,
                                pendingAmount = totalPendingAmount.toString(),
                                bsb = bsb,
                                balance = balance
                            )
                        }
                        SuccessResponseState(
                            data = TransactionResponseModel(
                                listOfTransactions = listOfTransactions,
                                listOfAtms = listOfAtms,
                                accountHeaderModel = accountHeaderModel
                            )
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
}