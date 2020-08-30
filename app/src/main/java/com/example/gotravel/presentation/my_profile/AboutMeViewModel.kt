package com.example.gotravel.presentation.my_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.utils.SingleLiveEvent
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.launch

class AboutMeViewModel(private val repository: DefaultUserRepository): ViewModel() {

    val state: MutableLiveData<AboutMeViewState> = MutableLiveData(AboutMeViewState())

    val successMessageEvent = SingleLiveEvent<String>()
    val errorMessageEvent = SingleLiveEvent<String>()

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        state.mutatePost { copy(userData = UserDataState.Loading) }
        val result = repository.getUserFromSharedPreferences()
        state.mutatePost { copy(userData = UserDataState.Content(result)) }
    }

    fun saveChanges(firstName: String, lastName: String, email: String, gender: String, birthDate: String, role: String = "user") {

        viewModelScope.launch {
            val password = "000000" //todo
            when(val result = repository.createOrUpdateUser(firstName, lastName, email, password, gender, birthDate, role)) {
                is Result.Success -> {
                    successMessageEvent.postValue("Successfully updated your profile.")
                    repository.saveUserDetails(result.data)
                }
                is Result.Failure -> {
                    errorMessageEvent.postValue("Error has occurred.")
                }

            }
        }
    }
}