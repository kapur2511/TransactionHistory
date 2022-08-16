package com.cba.transactions.domain.usecase

import android.text.SpannableStringBuilder
import com.cba.transactions.R
import com.cba.transactions.data.apimodels.AccountApiModel
import com.cba.transactions.domain.models.*
import com.cba.transactions.domain.uistate.TransactionUIState

object InstrumentationTransactionStub {

    fun getSuccessTransactionUIState(): TransactionUIState {
        return TransactionUIState(
            isLoading = false,
            error = null,
            accountName = AccountNameUIModel("my name"),
            list = listOf(
                AccountHeaderUIModel(
                    availableAmount = "$289.00",
                    balance = SpannableStringBuilder("Balance $123.00"),
                    pendingAmount = SpannableStringBuilder("Pending -$195.00"),
                    accountNumberAndBsb = SpannableStringBuilder("BSB 1234 Account 78788778")
                ),
                DateUIModel(
                    date = SpannableStringBuilder("Sun 28 Feb 534 days ago")
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("PENDING: some random expense"),
                    amount = "-$39.00"
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("PENDING: some random expense"),
                    amount = "-$39.00"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("Sat 27 Feb 535 days ago")
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("PENDING: some random expense"),
                    amount = "-$39.00"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("Thu 25 Feb 537 days ago")
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("PENDING: some random expense"),
                    amount = "-$39.00"
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("PENDING: some random expense"),
                    amount = "-$39.00"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("Tue 23 Feb 539 days ago")
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-$39.00"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("Mon 22 Feb 540 days ago")
                ),
                TransactionUIModel(
                    transactionId = "quyiuy11",
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-$39.00"
                )
            )
        )
    }

    fun getSuccessTransactionResponse(): TransactionResponseModel {
        return TransactionResponseModel(
            pendingAmount = "-195",
            accountModel = AccountApiModel(
                bsb = "1234",
                accountName = "my name",
                accountNumber = "78788778",
                available = "289",
                balance = "123"
            ),
            listOfAtms = listOf(),
            listOfTransactions = getListOfTransactions()
        )
    }

    fun getSuccessTransactionResponseShuffled(): TransactionResponseModel {
        return TransactionResponseModel(
            pendingAmount = "-195",
            accountModel = AccountApiModel(
                bsb = "1234",
                accountName = "my name",
                accountNumber = "78788778",
                available = "289",
                balance = "123"
            ),
            listOfAtms = listOf(),
            listOfTransactions = getListOfTransactions().shuffled()
        )
    }

    private fun getListOfTransactions(): List<TransactionModel> {
        return listOf(
            TransactionModel(
                isPending = true,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-28"
            ),
            TransactionModel(
                isPending = true,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-28"
            ),
            TransactionModel(
                isPending = true,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-27"
            ),
            TransactionModel(
                isPending = true,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-25"
            ),
            TransactionModel(
                isPending = true,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-25"
            ),
            TransactionModel(
                isPending = false,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-23"
            ),
            TransactionModel(
                isPending = false,
                amount = "-39",
                transactionId = "quyiuy11",
                description = "some random expense",
                category = "uncategorised",
                effectiveDate = "2021-02-22"
            )
        )
    }
}