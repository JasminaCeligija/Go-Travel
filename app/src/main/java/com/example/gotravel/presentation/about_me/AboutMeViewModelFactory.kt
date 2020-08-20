package com.example.gotravel.presentation.about_me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gotravel.data.DefaultUserRepository
import java.lang.IllegalArgumentException

class AboutMeViewModelFactory(
    private val repository: DefaultUserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutMeViewModel::class.java)) {
            return AboutMeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}