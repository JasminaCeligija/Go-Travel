package com.example.gotravel.presentation.trip_details.trip_plan

import com.example.gotravel.common.model.TripDay

data class SeeTripPlanViewState(
    val tripPlanViewState: TripPlanState = TripPlanState.Loading
)

sealed class TripPlanState {
    object Loading: TripPlanState()
    object Error: TripPlanState()
    data class Content(val tripDays: List<TripDay>): TripPlanState()
}
