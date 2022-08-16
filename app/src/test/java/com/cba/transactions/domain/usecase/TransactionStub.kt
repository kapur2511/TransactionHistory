package com.cba.transactions.domain.usecase

import android.text.SpannableStringBuilder
import com.cba.transactions.R
import com.cba.transactions.data.apimodels.AccountApiModel
import com.cba.transactions.domain.models.*
import com.cba.transactions.domain.uistate.TransactionUIState

object TransactionStub {

    fun getSuccessTransactionUIState(): TransactionUIState {
        return TransactionUIState(
            isLoading = false,
            error = null,
            accountName = AccountNameUIModel("my name"),
            list = listOf(
                AccountHeaderUIModel(
                    availableAmount = "289",
                    balance = SpannableStringBuilder("123"),
                    pendingAmount = SpannableStringBuilder("-156"),
                    accountNumberAndBsb = SpannableStringBuilder("BSB 1234 Account 78788778")
                ),
                DateUIModel(
                    date = SpannableStringBuilder("2021-02-28")
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("2021-02-27")
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("2021-02-25")
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("2021-02-23")
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                ),
                DateUIModel(
                    date = SpannableStringBuilder("2021-02-23")
                ),
                TransactionUIModel(
                    imageSrc = R.drawable.icon_category_uncategorised,
                    description = SpannableStringBuilder("some random expense"),
                    amount = "-39"
                )
            )
        )
    }

    fun getSuccessTransactionResponse(): TransactionResponseModel {
        return TransactionResponseModel(
            pendingAmount = "-49",
            accountModel = AccountApiModel(
                bsb = "1234",
                accountName = "my name",
                accountNumber = "78788778",
                available = "289",
                balance = "123"
            ),
            listOfAtms = listOf(),
            listOfTransactions = listOf(
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
                    isPending = false,
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
        )
    }
}