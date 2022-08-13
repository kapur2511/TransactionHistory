package com.cba.transactions.di

import com.cba.transactions.data.api.TransactionApi
import com.cba.transactions.data.datasource.TransactionRemoteDatasource
import com.cba.transactions.data.datasource.TransactionRemoteDatasourceImpl
import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.data.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
abstract class TransactionModule {

    private val baseUrl = "https://www.dropbox.com"

    @Binds
    abstract fun bindTransactionRepository(
        transactionRepository: TransactionRepositoryImpl
    ): TransactionRepository


    @Binds
    abstract fun bindTransactionDataSource(
        transactionRemoteDatasourceImpl: TransactionRemoteDatasourceImpl
    ): TransactionRemoteDatasource

    @Provides
    fun providesTransactionApi(
        okHttpClient: OkHttpClient,
        factory: Converter.Factory
    ): TransactionApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(factory)
        .client(okHttpClient)
        .build()
        .create(TransactionApi::class.java)

}