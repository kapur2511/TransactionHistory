package com.cba.transactions.ui.transactiondetails

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.cba.transactions.databinding.ActivityTransactionDetailsBinding
import com.cba.transactions.domain.models.TransactionModel

class TransactionDetailsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityTransactionDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityTransactionDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupUI()
    }

    private fun setupUI() {
        intent.getParcelableExtra<TransactionModel>(TRANSACTION_DETAIL_DATA)?.let { model ->
            with(viewBinding) {
                tvTransactionId.text = "Transaction id: ${model.transactionId}"
                tvTransactionAmount.text = "Amount: ${model.amount}"
                tvTransactionCategory.text = "Category: ${model.category}"
                tvTransactionDate.text = "Transaction date: ${model.effectiveDate}"
                val htmlResult = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Html.fromHtml(model.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(model.description)
                }
                tvTransactionDescription.text = "Description: $htmlResult"
                tvTransactionPending.text =
                    if (model.isPending) {
                        "Statue: Pending"
                    } else {
                        "Status: Not pending"
                    }
            }
        }
    }

    companion object {
        private const val TAG = "TransactionDetailsActivity"
        const val TRANSACTION_DETAIL_DATA = "TRANSACTION_DETAIL_DATA"
    }
}