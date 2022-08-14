package com.cba.transactions.domain.usecase

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import com.cba.transactions.R
import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.di.IoDispatcher
import com.cba.transactions.domain.models.*
import com.cba.transactions.domain.uistate.TransactionUIState
import com.cba.transactions.util.ErrorResponseState
import com.cba.transactions.util.SuccessResponseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
                val listOfModels = getListOfModels(transactionResponseModel = result.data)
                TransactionUIState(
                    list = listOfModels,
                    accountName = result.data.accountNameUIModel,
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
        val list = mutableListOf<BaseModel>()
        list.add(transactionResponseModel.accountHeaderUIModel)

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

        val sortedMap = dateTransactionMap.entries.sortedByDescending {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.parse(it.key)?.time
        }.associate {
            it.toPair()
        }


        sortedMap.forEach { entry ->
            val sdf = SimpleDateFormat("EEE-dd-MMM", Locale.getDefault())
            val date = sdf.parse(entry.key)

            val dateStringBuilder = StringBuilder(sdf.format(entry.key))

            if (date != null) {
                val diff: Long = Calendar.getInstance().time.time - date.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24
                dateStringBuilder.append(" $days days ago")
            }

            val dateModel = DateUIModel(date = dateStringBuilder.toString())
            list.add(dateModel)

            entry.value.forEach { transactionModel ->
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
                    val pendingText = "PENDING:"
                    val spannableString = SpannableString(pendingText)
                    spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), 0, pendingText.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                    descriptionBuilder.append(spannableString)
                }
                descriptionBuilder.append(" ${transactionModel.description}")
                list.add(
                    TransactionUIModel(
                        imageSrc = imageSrc,
                        description = descriptionBuilder.toString(),
                        amount = transactionModel.amount
                    )
                )
            }
        }


        return list
    }

}