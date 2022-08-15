package com.cba.transactions.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cba.transactions.databinding.ActivityMainBinding
import com.cba.transactions.domain.models.AccountNameUIModel
import com.cba.transactions.domain.models.BaseModel
import com.cba.transactions.domain.viewmodels.TransactionViewModel
import com.cba.transactions.ui.adapters.TransactionListAdapter
import com.cba.transactions.ui.renderer.TransactionListUIRenderer
import com.cba.transactions.ui.renderer.TransactionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    }

    override fun showLoading() {

    }

    override fun showSuccess(accountNameUIModel: AccountNameUIModel, list: List<BaseModel>) {
        transactionAdapter.setData(list)
    }

    override fun showError(error: Throwable?) {

    }


    companion object {
        private const val TAG = "TransactionListActivity"
    }
}