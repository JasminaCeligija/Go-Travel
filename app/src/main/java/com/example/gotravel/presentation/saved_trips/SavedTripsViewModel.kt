package com.example.gotravel.presentation.saved_trips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.DefaultContentRepository
import com.example.gotravel.utils.mutatePost
import com.example.gotravel.common.Result
import kotlinx.coroutines.launch

class SavedTripsViewModel(private val repository: DefaultContentRepository): ViewModel() {

    val state: MutableLiveData<SavedTripsViewState> = MutableLiveData(SavedTripsViewState())

    init {
        getSavedTrips()
    }

    private fun getSavedTrips() {
        state.mutatePost { copy(savedTripsListState = SavedTripsListState.Loading) }
        viewModelScope.launch {
            when(val result = repository.getSavedTrips()) {
                is Result.Success -> {
                    state.mutatePost { copy(savedTripsListState = SavedTripsListState.Content(result.data)) }
                }
            }
        }
    }
}