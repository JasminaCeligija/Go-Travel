package com.example.gotravel.domain.usecases

abstract class UseCase<P, R> {

    operator fun invoke(params: P): R {
        return doWork(params)
    }

    abstract fun doWork(params: P): R
}

operator fun <R> UseCase<Unit, R>.invoke() = invoke(Unit)