package com.cba.transactions.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cba.transactions.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}