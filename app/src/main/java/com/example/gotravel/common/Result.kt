package com.example.gotravel.common

sealed class Result<out ResponseType> {
    open class Success<out ResponseType>(val data: ResponseType): Result<ResponseType>()
    data class Failure(val error: Error): Result<Nothing>()
}