package com.example.gotravel.presentation.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.domain.UserDao
import kotlinx.coroutines.Dispatchers
import com.example.gotravel.common.Result
import com.example.gotravel.data.DefaultUserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: DefaultUserRepository) :
    ViewModel() {


    fun loginUser(email: String, password: String) {

        viewModelScope.launch {
            val result = repository.loginUser(email, password)
            when(result) {
                is Result.Success -> {
                    println("uuuuuuuuuu" + result.data.email +  result.data.password)
                }
                is Result.Failure -> {
                    print("uuuuuuuuuuuuuuushit")
                }
            }
        }
    }

}