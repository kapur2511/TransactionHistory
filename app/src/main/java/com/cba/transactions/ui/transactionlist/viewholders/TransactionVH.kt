package com.cba.transactions.ui.transactionlist.viewholders

import android.view.View
import com.cba.transactions.databinding.LayoutTransactionViewBinding
import com.cba.transactions.domain.models.TransactionUIModel
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback

class TransactionVH(view: View): BaseViewHolder<TransactionUIModel>(view) {

    private val binding = LayoutTransactionViewBinding.bind(view)

    override fun bind(data: TransactionUIModel, widgetCallback: WidgetCallback) {
        binding.apply {
            root.setOnClickListener {
                widgetCallback.onClick(data)
            }
            tvDescription.text = data.description
            tvAmount.text = data.amount
            ivCategory.setImageResource(data.imageSrc)
        }
    }
}