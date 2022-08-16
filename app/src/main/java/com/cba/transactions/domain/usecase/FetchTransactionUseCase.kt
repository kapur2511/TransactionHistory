package com.cba.transactions.domain.usecase

import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import com.cba.transactions.R
import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.di.IoDispatcher
import com.cba.transactions.domain.models.*
import com.cba.transactions.domain.uistate.TransactionUIState
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.SuccessResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FetchTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        when (val result = transactionRepository.getTransactions()) {
            is SuccessResponseState -> {
                val accountNameModel = AccountNameUIModel(
                    accountName = result.data.accountModel.accountName
                )
                val listOfModels = getListOfModels(transactionResponseModel = result.data)
                TransactionUIState(
                    list = listOfModels,
                    accountName = accountNameModel,
                    isLoading = false,
                    error = null
                )
            }
            is ErrorResponseState -> {
                TransactionUIState(
                    list = null,
                    accountName = null,
                    isLoading = false,
                    error = result.throwable
                )
            }
        }
    }

    private fun getListOfModels(
        transactionResponseModel: TransactionResponseModel
    ): List<BaseModel> {
        val formatter: DecimalFormat? = NumberFormat.getCurrencyInstance(Locale.getDefault()) as? DecimalFormat
        // uncomment this for dynamic currency symbol based on locale
        // val symbol = formatter?.currency?.symbol
        formatter?.apply {
            negativePrefix = "-$"
            negativeSuffix = ""
            positivePrefix = "$"
            positiveSuffix = ""
        }

        val list = mutableListOf<BaseModel>()
        list.add(
            getAccountHeaderUIModel(
                formatter = formatter,
                transactionResponseModel = transactionResponseModel
            )
        )
        Log.d(TAG, "$transactionResponseModel")
        val dateTransactionMap = hashMapOf<String, MutableList<TransactionModel>>()
        transactionResponseModel.listOfTransactions.forEach { transactionModel ->
            val date = transactionModel.effectiveDate
            if (dateTransactionMap.containsKey(date)) {
                val listOfTransaction = dateTransactionMap[date]
                listOfTransaction?.add(transactionModel)
            } else {
                dateTransactionMap[date] = mutableListOf(transactionModel)
            }
        }

        Log.d(TAG, "Unsorted map: $dateTransactionMap")
        val sortedMap = sortDateMapInDescendingOrder(dateTransactionMap = dateTransactionMap)
        Log.d(TAG, "Sorted map: $sortedMap")

        sortedMap.forEach { entry ->
            list.add(getDateUIModel(entry = entry))
            entry.value.forEach { transactionModel ->
                list.add(
                    getTransactionUIModel(
                        transactionModel = transactionModel,
                        formatter = formatter
                    )
                )
            }
        }
        return list
    }

    private fun getAccountHeaderUIModel(
        formatter: DecimalFormat?,
        transactionResponseModel: TransactionResponseModel
    ): AccountHeaderUIModel {
        return with(transactionResponseModel.accountModel) {
            val bsb = "BSB "
            val account = " Account "
            val bsbString = SpannableStringBuilder(bsb)
            bsbString.append(this.bsb)
            bsbString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, bsb.length, android.text.Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

            val accountString = SpannableStringBuilder(account)
            accountString.append(this.accountNumber)
            accountString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, account.length, android.text.Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

            val balance = "Balance "
            val balanceString = SpannableStringBuilder(balance)
            balanceString.append("${formatter?.format(this.balance.toDouble())}")
            balanceString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), balance.length, balanceString.length, android.text.Spannable.SPAN_INCLUSIVE_EXCLUSIVE)


            val pending = "Pending "
            val pendingString = SpannableStringBuilder(pending)
            pendingString.append("${formatter?.format(transactionResponseModel.pendingAmount.toDouble())}")
            pendingString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), pending.length, pendingString.length, android.text.Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

            AccountHeaderUIModel(
                availableAmount = "${formatter?.format(this.available.toDouble())}",
                pendingAmount = pendingString,
                balance = balanceString,
                accountNumberAndBsb = SpannableStringBuilder(bsbString)
                    .append(accountString)
            )
        }
    }

    private fun sortDateMapInDescendingOrder(
        dateTransactionMap: HashMap<String, MutableList<TransactionModel>>
    ): Map<String, MutableList<TransactionModel>> {
        return dateTransactionMap.entries.sortedByDescending {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.parse(it.key)?.time
        }.associate {
            it.toPair()
        }
    }

    private fun getDateUIModel(entry: Map.Entry<String, MutableList<TransactionModel>>): DateUIModel {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(entry.key)
        val dateStringBuilder = SpannableStringBuilder()
        if (date != null) {
            val sdfForUI = SimpleDateFormat("EEE dd MMM", Locale.getDefault())
            dateStringBuilder.append(sdfForUI.format(date))
            dateStringBuilder.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, dateStringBuilder.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            val diff: Long = Calendar.getInstance().time.time - date.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            dateStringBuilder.append(" $days days ago")
        }

        return DateUIModel(date = dateStringBuilder)
    }

    private fun getTransactionUIModel(
        transactionModel: TransactionModel,
        formatter: DecimalFormat?
    ): TransactionUIModel {
        val imageSrc = when (transactionModel.category) {
            "business" -> R.drawable.icon_category_business
            "cards" -> R.drawable.icon_category_cards
            "cash" -> R.drawable.icon_category_cash
            "categories" -> R.drawable.icon_category_categories
            "eatingOut" -> R.drawable.icon_category_eating_out
            "education" -> R.drawable.icon_category_education
            "entertainment" -> R.drawable.icon_category_entertainment
            "groceries" -> R.drawable.icon_category_groceries
            "shopping" -> R.drawable.icon_category_shopping
            "transport" -> R.drawable.icon_category_transportation
            "travel" -> R.drawable.icon_category_travel
            "uncategorised" -> R.drawable.icon_category_uncategorised
            else -> R.drawable.icon_category_uncategorised
        }
        val descriptionBuilder = SpannableStringBuilder()
        if (transactionModel.isPending) {
            val pendingText = "PENDING: "
            val spannableString = SpannableString(pendingText)
            spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, pendingText.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            descriptionBuilder.append(spannableString)
        }
        val htmlResult = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(transactionModel.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(transactionModel.description)
        }
        descriptionBuilder.append(htmlResult)
        return TransactionUIModel(
            imageSrc = imageSrc,
            description = descriptionBuilder,
            amount = formatter?.format(transactionModel.amount.toDouble()) ?: "$${transactionModel.amount}"
        )
    }

    companion object {
        private const val TAG = "FetchTransactionUseCase"
    }
}