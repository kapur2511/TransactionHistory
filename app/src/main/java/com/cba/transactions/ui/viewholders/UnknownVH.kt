package com.cba.transactions.ui.viewholders

import android.view.View
import com.cba.transactions.domain.models.BaseModel

class UnknownVH(view: View): BaseViewHolder<BaseModel>(view) {

    override fun bind(data: BaseModel) {
        /*no-op*/
    }
}