package com.cba.transactions.ui.transactionlist.viewholders

import android.view.View
import com.cba.transactions.databinding.LayoutDateViewBinding
import com.cba.transactions.domain.models.DateUIModel
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback

class DateVH(view: View): BaseViewHolder<DateUIModel>(view) {

    private val binding = LayoutDateViewBinding.bind(view)

    override fun bind(data: DateUIModel, widgetCallback: WidgetCallback) {
        binding.apply {
            tvDate.text = data.date
        }
    }
}