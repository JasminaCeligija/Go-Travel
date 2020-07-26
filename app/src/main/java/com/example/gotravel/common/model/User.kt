package com.example.gotravel.common.model

data class User (
    val uuid: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String,
    val password: String,
    val gender: Gender?= null,
    val birthDate: Long? = null,
    val role: String? = null
)

enum class Gender {

    Male,
    Female,
    Other;

    override fun toString(): String {
        return name.capitalize()
    }
}