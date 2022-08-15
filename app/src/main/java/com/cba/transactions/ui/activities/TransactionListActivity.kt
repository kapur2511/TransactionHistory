package com.cba.transactions.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cba.transactions.R
import com.cba.transactions.databinding.ActivityMainBinding
import com.cba.transactions.domain.models.AccountNameUIModel
import com.cba.transactions.domain.models.BaseModel
import com.cba.transactions.viewmodels.TransactionViewModel
import com.cba.transactions.ui.adapters.TransactionListAdapter
import com.cba.transactions.ui.renderer.TransactionListUIRenderer
import com.cba.transactions.ui.renderer.TransactionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.TextStyle

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity(), TransactionView {


    private val viewModel: TransactionViewModel by viewModels()
    private val transactionRenderer = TransactionListUIRenderer(this)
    private val transactionAdapter = TransactionListAdapter()

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupUI()
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


    companion object {
        private const val TAG = "TransactionListActivity"
    }
}