package com.example.gotravel.presentation.saved_trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gotravel.data.DefaultContentRepository
import java.lang.IllegalArgumentException

class SavedTripsViewModelFactory (
    private val repository: DefaultContentRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedTripsViewModel::class.java)) {
            return SavedTripsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}