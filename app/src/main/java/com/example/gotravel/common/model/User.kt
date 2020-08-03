package com.example.gotravel.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @ColumnInfo(name = "first_name")
    val firstName: String? = null,
    @ColumnInfo(name = "last_name")
    val lastName: String? = null,
    @ColumnInfo(name =  "email")
    val email: String,
    @ColumnInfo(name =  "password")
    val password: String,
    @ColumnInfo(name =  "gender")
    val gender: String?= null,
    @ColumnInfo(name =  "birth_date")
    val birthDate: Long? = null,
    @ColumnInfo(name =  "role")
    val role: String? = null
) {
    @PrimaryKey(autoGenerate = true)
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