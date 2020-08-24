package com.example.gotravel.presentation.my_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.launch

class AboutMeViewModel(private val repository: DefaultUserRepository): ViewModel() {

    val state: MutableLiveData<AboutMeViewState> = MutableLiveData(AboutMeViewState())

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        state.mutatePost { copy(userData = UserDataState.Loading) }
        val result = repository.getUserFromSharedPreferences()
        state.mutatePost { copy(userData = UserDataState.Content(result)) }
    }

    fun saveChanges() {

    }

}