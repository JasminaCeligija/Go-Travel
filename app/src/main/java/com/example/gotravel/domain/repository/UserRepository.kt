package com.example.gotravel.domain.repository

import com.example.gotravel.common.model.User
import com.example.gotravel.common.Result

interface UserRepository {

    var user: User?

    suspend fun createOrUpdateUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        gender: String,
        birthDate: String,
        role: String
    ): Result<User>

    suspend fun loginUser(email: String, password: String): Result<User>
    suspend fun logoutUser(): Result<Unit>
}