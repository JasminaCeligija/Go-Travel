package com.example.gotravel.presentation.auth.create_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class CreateAccountViewModel(private val repository: DefaultUserRepository) : ViewModel() {

    val successMessageEvent = SingleLiveEvent<String>()
    val errorMessageEvent = SingleLiveEvent<String>()

    fun createUser(firstName: String, lastName: String, email: String, password: String, gender: String, birthDate: String, role: String) {
        viewModelScope.launch {
            when(repository.createOrUpdateUser(firstName, lastName, email, password, gender, birthDate, role)) {
                is Result.Success -> {
                    successMessageEvent.postValue("You have successfully created your account.")
                }
                is Result.Failure -> {
                    errorMessageEvent.postValue("Error has occurred.")
                }
            }
           /* val result = CreateUserUseCase(firstName, lastName, email, password, birthDate)
            when(result) {
                is Result.Success -> {
                    println("aaaaaa" + result.data.email +  result.data.password)
                }
                is Result.Failure -> {

                }
            } */
        }
    }
}