package com.cba.transactions.ui.transactionlist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cba.transactions.databinding.ActivityMainBinding
import com.cba.transactions.domain.models.AccountNameUIModel
import com.cba.transactions.domain.models.BaseModel
import com.cba.transactions.domain.models.TransactionUIModel
import com.cba.transactions.ui.events.ToastEvent
import com.cba.transactions.ui.events.TransactionDetailsEvent
import com.cba.transactions.ui.transactiondetails.TransactionDetailsActivity
import com.cba.transactions.ui.transactionlist.adapters.TransactionListAdapter
import com.cba.transactions.ui.transactionlist.interfaces.WidgetCallback
import com.cba.transactions.ui.transactionlist.renderer.TransactionListUIRenderer
import com.cba.transactions.ui.transactionlist.renderer.TransactionView
import com.cba.transactions.viewmodels.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity(), TransactionView, WidgetCallback {


    private val viewModel: TransactionViewModel by viewModels()
    private val transactionRenderer = TransactionListUIRenderer(this)
    private val transactionAdapter = TransactionListAdapter(this)

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupUI()
        setupUIEventListener()
        setupSwipeRefreshLayout()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactionUiState
                    .collect {
                        Log.d(TAG, "$it")
                        transactionRenderer.render(it)
                    }
            }
        }
    }

    private fun setupSwipeRefreshLayout() {
        with(viewBinding.swipeRefreshLayout) {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.getTransactions()
            }
        }
    }

    private fun setupUIEventListener() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect{ event ->
                when(event) {
                    is TransactionDetailsEvent -> {
                        val intent = Intent(this@TransactionListActivity, TransactionDetailsActivity::class.java)
                        intent.putExtra(TransactionDetailsActivity.TRANSACTION_DETAIL_DATA, event.model)
                        this@TransactionListActivity.startActivity(intent)
                    }
                    is ToastEvent -> {
                        Toast.makeText(
                            this@TransactionListActivity,
                            event.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupUI() {
        viewBinding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(
                this@TransactionListActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = transactionAdapter
        }

        viewBinding.errorContainer.btnRetry.setOnClickListener {
            viewModel.getTransactions()
        }
    }

    override fun showLoading() {
        viewBinding.apply {
            rvTransactions.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            errorContainer.errorRoot.visibility = View.GONE
        }
    }

    override fun showSuccess(accountNameUIModel: AccountNameUIModel, list: List<BaseModel>) {
        viewBinding.apply {
            rvTransactions.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            errorContainer.errorRoot.visibility = View.GONE
        }
        viewBinding.tvAccountName.text = accountNameUIModel.accountName
        transactionAdapter.setData(list)
    }

    override fun showError(error: Throwable?) {
        with(viewBinding) {
            errorContainer.errorRoot.visibility = View.VISIBLE
            rvTransactions.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    override fun onClick(model: BaseModel) {
        when (model) {
            is TransactionUIModel -> {
                viewModel.navigateToTransactionDetails(
                    transactionId = model.transactionId
                )
            }
            // can handle other clicks over here
            else -> {/*no-op*/}
        }
    }

    companion object {
        private const val TAG = "TransactionListActivity"
    }
}