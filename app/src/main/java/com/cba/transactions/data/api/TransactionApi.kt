package com.cba.transactions.data.api

import com.cba.transactions.data.apimodels.TransactionResponseApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_TRANSACTIONS_API = "/s/inyr8o29shntk9w/exercise.json"

interface TransactionApi {

    @GET(GET_TRANSACTIONS_API)
    suspend fun getTransactions(
        @Query("dl")
        searchText: Int = 2
    ): Response<TransactionResponseApiModel>
}