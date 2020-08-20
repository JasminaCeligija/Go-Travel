package com.example.gotravel.presentation.auth.login

import androidx.lifecycle.MutableLiveData
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
    val successfulLoginEvent = SingleLiveEvent<Unit>()
    val successfulLogoutEvent = SingleLiveEvent<Unit>()
    val isUserAuthenticated = MutableLiveData<Boolean>(false)


    fun loginUser(email: String, password: String) {
        showProgressBar.postValue(true)
        viewModelScope.launch {
            when(repository.loginUser(email, password)) {
                is Result.Success -> {
                    delay(2000)
                    showProgressBar.postValue(false)
                    isUserAuthenticated.postValue(true)
                    successfulLoginEvent.call()
                }
                is Result.Failure -> {
                    delay(2000)
                    isUserAuthenticated.postValue(false)
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
                    isUserAuthenticated.postValue(false)
                    successfulLogoutEvent.call()
                }
                /*is Result.Failure -> {
                }*/
            }
        }
    }

}