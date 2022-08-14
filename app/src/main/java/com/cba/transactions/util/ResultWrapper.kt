package com.cba.transactions.util

sealed class ResponseWrapper<T>

data class SuccessResponseState<T>(
    val data: T
): ResponseWrapper<T>()

data class ErrorResponseState<T>(
    val throwable: Throwable?
): ResponseWrapper<T>()