package com.example.gotravel.data

import kotlin.coroutines.CoroutineContext

data class ExecutionDispatchers(
    val ui: CoroutineContext,
    val computation: CoroutineContext,
    val io: CoroutineContext
)
