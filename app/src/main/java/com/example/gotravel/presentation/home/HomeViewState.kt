package com.example.gotravel.presentation.home

import com.example.gotravel.common.model.Trip

data class HomeViewState(
    val tripsViewState: TripsViewState = TripsViewState.Loading
)

sealed class TripsViewState {
    object Loading: TripsViewState()
    object Error: TripsViewState()
    data class Content(val trips: List<Trip>): TripsViewState()
}