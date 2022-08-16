package com.cba.transactions.ui.transactionlist.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cba.transactions.domain.models.BaseModel
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback

abstract class BaseViewHolder<T: BaseModel>(
    view: View
): RecyclerView.ViewHolder(view) {

    abstract fun bind(data: T, widgetCallback: WidgetCallback)
}