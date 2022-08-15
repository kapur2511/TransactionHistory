package com.cba.transactions.ui.viewholders

import android.view.View
import com.cba.transactions.databinding.LayoutDateViewBinding
import com.cba.transactions.domain.models.DateUIModel

class DateVH(view: View): BaseViewHolder<DateUIModel>(view) {

    private val binding = LayoutDateViewBinding.bind(view)

    override fun bind(data: DateUIModel) {
        binding.apply {
            tvDate.text = data.date
        }
    }
}