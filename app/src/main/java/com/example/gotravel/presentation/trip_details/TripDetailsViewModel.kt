package com.example.gotravel.presentation.trip_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.common.model.TripDay
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TripDetailsViewModel: ViewModel() {

    val state: MutableLiveData<TripDetailsViewState> = MutableLiveData(TripDetailsViewState())

    init {
        viewModelScope.launch {
            getTripPlan()
        }
    }

    private fun getTripPlan() {
        state.mutatePost { copy(tripPlanViewState = TripPlanViewState.Loading) }
        viewModelScope.launch {
            when (val result = getTripPlanUseCase()) {
                is Result.Success -> {
                    state.mutatePost {
                        copy(
                            tripPlanViewState = TripPlanViewState.Content(
                                result.data
                            )
                        )
                    }
                }
            }
        }

    }

    // TODO: create useCase to get trip plan from api
    private suspend fun getTripPlanUseCase() = withContext(Dispatchers.IO) {
        val mockedTripPlan = listOf(
            TripDay("1", "Day 1", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."),
            TripDay("2", "Day 2", "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages."),
            TripDay("3", "Day 3", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old."),
            TripDay("4","Day 4", "Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.")
        )
        return@withContext Result.Success(mockedTripPlan)
    }

}