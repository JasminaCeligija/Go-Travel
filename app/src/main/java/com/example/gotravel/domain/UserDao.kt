package com.example.gotravel.domain

import androidx.room.*
import com.example.gotravel.common.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(vararg user: User)

    @Query("SELECT * FROM users WHERE email = :email and password = :password")
    fun getUser(email: String, password: String): User?

    @Delete
    suspend fun deleteUser(user: User)
}