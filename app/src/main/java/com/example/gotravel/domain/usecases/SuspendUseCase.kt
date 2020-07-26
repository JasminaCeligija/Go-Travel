package com.example.gotravel.domain.usecases


abstract class SuspendUseCase<P, R> {

    suspend operator fun invoke(params: P): R {
        return doWork(params)
    }

    abstract suspend fun doWork(params: P): R
}

suspend operator fun <R> SuspendUseCase<Unit, R>.invoke() = invoke(Unit)