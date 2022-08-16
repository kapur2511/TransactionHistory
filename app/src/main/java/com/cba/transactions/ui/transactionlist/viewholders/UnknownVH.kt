package com.cba.transactions.ui.transactionlist.viewholders

import android.view.View
import com.cba.transactions.domain.models.BaseModel
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback

class UnknownVH(view: View): BaseViewHolder<BaseModel>(view) {

    override fun bind(data: BaseModel, widgetCallback: WidgetCallback) {
        /*no-op*/
    }
}