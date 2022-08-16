package com.cba.transactions.ui.transactionlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cba.transactions.R
import com.cba.transactions.domain.models.AccountHeaderUIModel
import com.cba.transactions.domain.models.BaseModel
import com.cba.transactions.domain.models.DateUIModel
import com.cba.transactions.domain.models.TransactionUIModel
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback
import com.cba.transactions.ui.transactionlist.viewholders.*
import java.lang.RuntimeException

class TransactionListAdapter(
    private val widgetCallback: WidgetCallback
): RecyclerView.Adapter<BaseViewHolder<BaseModel>>() {

    private val listOfModels = mutableListOf<BaseModel>()

    fun setData(list: List<BaseModel>) {
        listOfModels.clear()
        listOfModels.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseModel> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            ACCOUNT_HEADER_LAYOUT -> AccountHeaderVH(view)
            DATE_LAYOUT -> DateVH(view)
            TRANSACTION_LAYOUT -> TransactionVH(view)
            else -> UnknownVH(view)
        } as? BaseViewHolder<BaseModel> ?: throw RuntimeException("onCreateViewHolder failed for $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseModel>, position: Int) {
        holder.bind(listOfModels[position], widgetCallback)
    }

    override fun getItemViewType(position: Int): Int {
        return when(listOfModels[position]) {
            is AccountHeaderUIModel -> { ACCOUNT_HEADER_LAYOUT }
            is DateUIModel -> { DATE_LAYOUT }
            is TransactionUIModel -> { TRANSACTION_LAYOUT }
            else -> { UNKNOWN_LAYOUT }
        }
    }

    override fun getItemCount(): Int {
        return listOfModels.size
    }

    companion object {
        private const val ACCOUNT_HEADER_LAYOUT = R.layout.layout_account_header_view
        private const val DATE_LAYOUT = R.layout.layout_date_view
        private const val TRANSACTION_LAYOUT = R.layout.layout_transaction_view
        private const val UNKNOWN_LAYOUT = R.layout.layout_unknown_view
    }
}