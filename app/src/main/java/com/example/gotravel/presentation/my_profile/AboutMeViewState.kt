package com.example.gotravel.presentation.my_profile

import com.example.gotravel.common.model.User

data class AboutMeViewState(
    val userData: UserDataState = UserDataState.Loading
)

sealed class UserDataState {
    object Loading: UserDataState()
    object Error: UserDataState()
    data class Content(val data: User): UserDataState()
}