package com.cba.transactions.util

sealed class ResultWrapper<T>

data class LoadingState<T>(
    val showLoading: Boolean
): ResultWrapper<T>()

data class SuccessState<T>(
    val data: T
): ResultWrapper<T>()

data class ErrorState<T>(
    val throwable: Throwable?
): ResultWrapper<T>()


sealed class ResponseWrapper<T>

data class SuccessResponseState<T>(
    val data: T
): ResponseWrapper<T>()

data class ErrorResponseState<T>(
    val throwable: Throwable?
): ResponseWrapper<T>()