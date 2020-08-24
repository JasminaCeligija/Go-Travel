package com.example.gotravel.data

import android.content.SharedPreferences

class AppPreferences(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val KEY_USER_ID = "PREF_KEY_USER_ID"
        const val KEY_USER_FIRST_NAME = "PREF_KEY_USER_FIRST_NAME"
        const val KEY_USER_LAST_NAME = "PREF_KEY_USER_LAST_NAME"
        const val KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL"
        const val KEY_USER_PASSWORD = "PREF_KEY_USER_PASSWORD"
        const val KEY_USER_GENDER = "PREF_KEY_USER_GENDER"
        const val KEY_USER_ROLE = "PREF_KEY_USER_ROLE"
        const val KEY_USER_BIRTH_DATE = "PREF_KEY_USER_BIRTH_DATE"
    }

    fun setUserId(userId: Int) {
        sharedPreferences.edit().putInt(KEY_USER_ID, userId).apply()
    }

    fun setUserFirstName(firstName: String) {
        sharedPreferences.edit().putString(KEY_USER_FIRST_NAME, firstName).apply()
    }

    fun setUserLastName(lastName: String) {
        sharedPreferences.edit().putString(KEY_USER_LAST_NAME, lastName).apply()
    }

    fun setUserEmail(email: String) {
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun setUserPassword(password: String) {
        sharedPreferences.edit().putString(KEY_USER_PASSWORD, password).apply()
    }

    fun setUserGender(gender: String) {
        sharedPreferences.edit().putString(KEY_USER_GENDER, gender).apply()
    }

    fun setUserRole(role: String) {
        sharedPreferences.edit().putString(KEY_USER_ROLE, role).apply()
    }

    fun setUserBirthDate(birthDate: String) {
        sharedPreferences.edit().putString(KEY_USER_BIRTH_DATE, birthDate).apply()
    }

    fun getUserId(): String? = sharedPreferences.getString(KEY_USER_ID, null)
    fun getUserFirstName(): String? = sharedPreferences.getString(KEY_USER_FIRST_NAME, null)
    fun getUserLastName(): String? = sharedPreferences.getString(KEY_USER_LAST_NAME, null)
    fun getUserEmail(): String? = sharedPreferences.getString(KEY_USER_EMAIL, null)
    fun getUserPassword(): String? = sharedPreferences.getString(KEY_USER_PASSWORD, null)
    fun getUserGender(): String? = sharedPreferences.getString(KEY_USER_GENDER, null)
    fun getUserRole(): String? = sharedPreferences.getString(KEY_USER_ROLE, null)
    fun getUserBirthDate(): String? = sharedPreferences.getString(KEY_USER_BIRTH_DATE, null)
}