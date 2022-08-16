package com.cba.transactions.di

import com.cba.transactions.data.api.TransactionApi
import com.cba.transactions.data.datasource.TransactionRemoteDatasource
import com.cba.transactions.data.datasource.TransactionRemoteDatasourceImpl
import com.cba.transactions.data.repository.TransactionRepository
import com.cba.transactions.data.repository.TransactionRepositoryImpl
import com.cba.transactions.domain.usecase.FetchTransactionUseCase
import com.cba.transactions.domain.usecase.TransactionDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
class TransactionModule {

    private val baseUrl = "https://www.dropbox.com"

    @Provides
    @ViewModelScoped
    fun providesTransactionUseCase(
        transactionRepository: TransactionRepository,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): FetchTransactionUseCase = FetchTransactionUseCase(
        transactionRepository = transactionRepository,
        ioDispatcher = coroutineDispatcher
    )

    @Provides
    @ViewModelScoped
    fun providesTransactionDetailsUseCase(
        transactionRepository: TransactionRepository,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): TransactionDetailsUseCase = TransactionDetailsUseCase(
        transactionRepository = transactionRepository,
        ioDispatcher = coroutineDispatcher
    )

    @Provides
    @ViewModelScoped
    fun providesTransactionRepository(
        transactionRemoteDatasource: TransactionRemoteDatasource,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): TransactionRepository = TransactionRepositoryImpl(
        transactionRemoteDatasource = transactionRemoteDatasource,
        ioCoroutineDispatcher = coroutineDispatcher
    )

    @Provides
    @ViewModelScoped
    fun bindTransactionDataSource(
        transactionApi: TransactionApi,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): TransactionRemoteDatasource = TransactionRemoteDatasourceImpl(
        ioCoroutineDispatcher = coroutineDispatcher,
        transactionApi = transactionApi
    )

    @Provides
    @ViewModelScoped
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