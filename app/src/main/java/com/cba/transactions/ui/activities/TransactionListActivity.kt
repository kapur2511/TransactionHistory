package com.cba.transactions.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cba.transactions.R
import com.cba.transactions.databinding.ActivityMainBinding
import com.cba.transactions.domain.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: TransactionViewModel
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Bind the visibility of the progressBar to the state
                // of isFetchingArticles.
                viewModel.transactionUiState
//                    .map { it.isLoading }
//                    .distinctUntilChanged()
                    .collect {
                        Log.d(TAG, "$it")
                    }
            }
        }
    }

    companion object {
        private const val TAG = "TransactionListActivity"
    }
}