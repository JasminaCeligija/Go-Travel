package com.example.gotravel.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.common.model.User
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: DefaultUserRepository) :
    ViewModel() {

    val showProgressBar = SingleLiveEvent<Boolean>()
    val errorMessageEvent = SingleLiveEvent<String>()
    val successfulLoginEvent = SingleLiveEvent<Unit>()
    val successfulLogoutEvent = SingleLiveEvent<Unit>()
    val authenticatedUser = MutableLiveData<User>(null)

    fun loginUser(email: String, password: String) {
        showProgressBar.postValue(true)
        viewModelScope.launch {
            when(val result = repository.loginUser(email, password)) {
                is Result.Success -> {
                    delay(1500)
                    showProgressBar.postValue(false)
                    authenticatedUser.postValue(result.data)
                    successfulLoginEvent.call()
                }
                is Result.Failure -> {
                    delay(1500)
                    authenticatedUser.postValue(null)
                    errorMessageEvent.postValue("Error has occurred.")
                }
            }
        }
    }

    fun logoutUser() {
        showProgressBar.postValue(true)
        viewModelScope.launch {
            when(repository.logoutUser()) {
                is Result.Success -> {
                    delay(2000)
                    showProgressBar.postValue(false)
                    authenticatedUser.postValue(null)
                    successfulLogoutEvent.call()
                }
                /*is Result.Failure -> {
                }*/
            }
        }
    }
}