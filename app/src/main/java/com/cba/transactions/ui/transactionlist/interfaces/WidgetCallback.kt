package com.cba.transactions.ui.transactionlist.interfaces

import com.cba.transactions.domain.models.BaseModel

interface WidgetCallback {

    fun onClick(model: BaseModel)
}