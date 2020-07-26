package com.example.gotravel.domain

import com.example.gotravel.common.model.User
import com.example.gotravel.common.Result

interface UserRepository {

    var user: User?

    fun isUserAuthenticated(): Boolean

    suspend fun loginUser(email: String, password: String): Result<User>
    suspend fun createUser(firstName: String, lastName: String, email: String, password: String, birthDate: Long): Result<User>
    suspend fun logoutUser(): Result<Unit>
}