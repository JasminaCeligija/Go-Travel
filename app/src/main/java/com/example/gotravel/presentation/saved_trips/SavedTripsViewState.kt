package com.example.gotravel.presentation.saved_trips

import com.example.gotravel.common.model.Trip

data class SavedTripsViewState(
    val savedTripsListState: SavedTripsListState = SavedTripsListState.Loading
)

sealed class SavedTripsListState {
    object Loading: SavedTripsListState()
    object Error: SavedTripsListState()
    data class Content(val trips: List<Trip>): SavedTripsListState()
}