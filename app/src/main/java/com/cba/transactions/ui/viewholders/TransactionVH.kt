package com.cba.transactions.ui.viewholders

import android.view.View
import com.cba.transactions.databinding.LayoutTransactionViewBinding
import com.cba.transactions.domain.models.TransactionUIModel

class TransactionVH(view: View): BaseViewHolder<TransactionUIModel>(view) {

    private val binding = LayoutTransactionViewBinding.bind(view)

    override fun bind(data: TransactionUIModel) {
        binding.apply {
            tvDescription.text = data.description
            tvAmount.text = data.amount
            ivCategory.setImageResource(data.imageSrc)
        }
    }
}