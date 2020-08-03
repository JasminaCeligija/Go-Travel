package com.example.gotravel.presentation.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.domain.UserDao
import kotlinx.coroutines.Dispatchers
import com.example.gotravel.common.Result
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val database: UserDao, application: Application) :
    AndroidViewModel(application) {




    fun loginUser(email: String, password: String) {

        viewModelScope.launch {
            val result = loginUserUseCase(email, password)
            when(result) {
                is Result.Success -> {
                    println("uuuuuuuuuu" + result.data.email +  result.data.password)
                }
            }
        }
    }

    private suspend fun loginUserUseCase(email: String, password: String) = withContext(Dispatchers.IO) {
        val user = database.loginUser(email, password)
        return@withContext Result.Success(user)
    }

}