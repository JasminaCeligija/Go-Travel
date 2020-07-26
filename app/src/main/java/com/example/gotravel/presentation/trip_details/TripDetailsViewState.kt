package com.example.gotravel.presentation.trip_details

import com.example.gotravel.common.model.TripDay

data class TripDetailsViewState(
    val tripPlanViewState: TripPlanViewState = TripPlanViewState.Loading
)

sealed class TripPlanViewState {
    object Loading: TripPlanViewState()
    object Error: TripPlanViewState()
    data class Content(val tripDays: List<TripDay>): TripPlanViewState()
}