package com.cba.transactions.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cba.transactions.domain.models.BaseModel

abstract class BaseViewHolder<T: BaseModel>(
    private val view: View
): RecyclerView.ViewHolder(view) {

    abstract fun bind(data: T)
}