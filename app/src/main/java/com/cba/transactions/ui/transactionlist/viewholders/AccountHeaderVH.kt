package com.cba.transactions.ui.transactionlist.viewholders

import android.view.View
import com.cba.transactions.databinding.LayoutAccountHeaderViewBinding
import com.cba.transactions.domain.models.AccountHeaderUIModel
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback

class AccountHeaderVH(view: View): BaseViewHolder<AccountHeaderUIModel>(view) {

    private val binding = LayoutAccountHeaderViewBinding.bind(view)

    override fun bind(data: AccountHeaderUIModel, widgetCallback: WidgetCallback) {
        binding.apply {
            tvAvailableAmount.text = data.availableAmount
            tvBalanceAmount.text = data.balance
            tvPendingAmount.text = data.pendingAmount
            tvBsbAndAccount.text = data.accountNumberAndBsb
        }
    }
}