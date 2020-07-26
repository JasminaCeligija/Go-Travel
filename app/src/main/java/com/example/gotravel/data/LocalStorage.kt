package com.example.gotravel.data

import android.content.Context
import com.example.gotravel.common.model.User
import com.google.gson.Gson

class LocalStorage(context: Context) {

    private val gson = Gson()

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)


    fun getUser(): User? {
        val value = sharedPreferences.getString(USER_KEY, null)
        return gson.fromJson(value, User::class.java)
    }

    fun putUser(user: User) {
        val jsonString = gson.toJson(user)
        sharedPreferences.edit().putString(USER_KEY, jsonString).apply()
    }

    fun clearUser() {
        sharedPreferences.edit().remove(USER_KEY).apply()
    }

    companion object {
        private const val SHARED_PREF_FILE_NAME = "com.example.gotravel.data.preferences"
        private const val USER_KEY = "user.key"
    }
}
