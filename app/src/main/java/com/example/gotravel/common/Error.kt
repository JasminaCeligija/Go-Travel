package com.example.gotravel.common

sealed class Error {
    class InvalidDataError(val message: String?) : Error()
    class ConnectionError(val message: String?) : Error()
    class ParseError(val message: String?) : Error()
    class HttpError(val httpStatusCode: Int, val errorBody: ByteArray?) : Error()
    class UnauthorizedError(val message: String?) : Error()
    class GenericError(val error: Throwable) : Error()
}