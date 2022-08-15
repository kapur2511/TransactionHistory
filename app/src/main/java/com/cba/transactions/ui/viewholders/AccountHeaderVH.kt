package com.cba.transactions.ui.viewholders

import android.view.View
import com.cba.transactions.databinding.LayoutAccountHeaderViewBinding
import com.cba.transactions.domain.models.AccountHeaderUIModel

class AccountHeaderVH(view: View): BaseViewHolder<AccountHeaderUIModel>(view) {

    private val binding = LayoutAccountHeaderViewBinding.bind(view)

    override fun bind(data: AccountHeaderUIModel) {
        binding.apply {
            tvAvailableAmount.text = data.availableAmount
            tvBalanceAmount.text = data.balance
            tvPendingAmount.text = data.pendingAmount
            tvBsbAndAccount.text = "BSB ${data.bsb}  Account ${data.accountNumber}"
        }
    }
}