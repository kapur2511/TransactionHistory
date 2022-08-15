package com.cba.transactions.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cba.transactions.databinding.ActivityMainBinding
import com.cba.transactions.domain.viewmodels.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {


    private val viewModel: TransactionViewModel by viewModels()

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