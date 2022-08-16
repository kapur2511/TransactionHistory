package com.cba.transactions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cba.transactions.di.IoDispatcher
import com.cba.transactions.domain.uistate.TransactionUIState
import com.cba.transactions.domain.usecase.FetchTransactionUseCase
import com.cba.transactions.domain.usecase.TransactionDetailsUseCase
import com.cba.transactions.ui.events.ToastEvent
import com.cba.transactions.ui.events.TransactionDetailsEvent
import com.cba.transactions.ui.events.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val fetchTransactionUseCase: FetchTransactionUseCase,
    private val transactionDetailsUseCase: TransactionDetailsUseCase,
    @IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _transactionUiState = MutableStateFlow(TransactionUIState())
    val transactionUiState: StateFlow<TransactionUIState> = _transactionUiState
    private var job: Job? = null

    private val eventChannel = Channel<UIEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        getTransactions()
    }

    fun getTransactions() {
        job?.cancel()
        job = viewModelScope.launch(ioCoroutineDispatcher) {
            // show loader
            _transactionUiState.emit(
                _transactionUiState.value.copy(
                    isLoading = true
                )
            )
            try {
                val result = fetchTransactionUseCase()
                _transactionUiState.emit(
                    _transactionUiState.value.copy(
                        isLoading = false,
                        list = result.list,
                        accountName = result.accountName,
                        error = null
                    )
                )
            } catch (e: Exception) {
                _transactionUiState.emit(
                    _transactionUiState.value.copy(
                        list = null,
                        accountName = null,
                        isLoading = false,
                        error = e,
                    )
                )
            }
        }
    }

    fun navigateToTransactionDetails(
        transactionId: String
    ) {
        viewModelScope.launch(ioCoroutineDispatcher) {
            val model = transactionDetailsUseCase(transactionId)
            if (model != null) {
                eventChannel.send(TransactionDetailsEvent(model = model))
            } else {
                eventChannel.send(ToastEvent(message = "Can't open transaction details."))
            }
        }
    }
}