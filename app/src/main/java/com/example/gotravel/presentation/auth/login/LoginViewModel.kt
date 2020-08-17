package com.example.gotravel.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: DefaultUserRepository) :
    ViewModel() {

    val showProgressBar = SingleLiveEvent<Boolean>()
    val errorMessageEvent = SingleLiveEvent<String>()
    val navigateToHomeEvent = SingleLiveEvent<Unit>()

    fun loginUser(email: String, password: String) {
        showProgressBar.postValue(true)
        viewModelScope.launch {
            val result = repository.loginUser(email, password)
            when(result) {
                is Result.Success -> {
                    delay(2000)
                    showProgressBar.postValue(false)
                    navigateToHomeEvent.call()
                }
                is Result.Failure -> {
                    delay(2000)
                    errorMessageEvent.postValue("Error has occurred.")
                }
            }
        }
    }
}