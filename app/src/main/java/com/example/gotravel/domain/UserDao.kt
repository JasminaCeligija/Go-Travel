package com.example.gotravel.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gotravel.common.model.User

@Dao
interface UserDao {

    //update or insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email and password = :password")
    fun loginUser(email: String, password: String): User
}