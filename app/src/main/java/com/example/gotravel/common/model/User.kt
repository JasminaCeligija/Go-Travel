package com.example.gotravel.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name =  "email")
    val email: String,
    @ColumnInfo(name =  "password")
    val password: String,
    @ColumnInfo(name =  "gender")
    val gender: String,
    @ColumnInfo(name =  "birth_date")
    val birthDate: Long,
    @ColumnInfo(name =  "role")
    val role: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id: Int = 0
}

/*
enum class Gender {

    Male,
    Female,
    Other;

    override fun toString(): String {
        return name.capitalize()
    }
}*/