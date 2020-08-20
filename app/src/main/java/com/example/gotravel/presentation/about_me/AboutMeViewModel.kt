package com.example.gotravel.presentation.about_me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AboutMeViewModel(private val repository: DefaultUserRepository): ViewModel() {

    val successfullyLogedOutEvent = SingleLiveEvent<Unit>()

    fun logoutUser() {
        viewModelScope.launch {
            when(repository.logoutUser()) {
                is Result.Success -> {
                    successfullyLogedOutEvent.call()
                }
                /*is Result.Failure -> {
                }*/
            }
        }
    }

}