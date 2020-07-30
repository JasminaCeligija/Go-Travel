package com.example.gotravel.presentation.trip_details.trip_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.common.Result
import com.example.gotravel.common.model.TripDay
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TripPlanViewModel: ViewModel() {

    val state: MutableLiveData<SeeTripPlanViewState> = MutableLiveData(SeeTripPlanViewState())

    init {
        viewModelScope.launch {
            getTripPlan()
        }
    }

    private fun getTripPlan() {
        state.mutatePost { copy(tripPlanViewState = TripPlanState.Loading) }
        viewModelScope.launch {
            when (val result = getTripPlanUseCase()) {
                is Result.Success -> {
                    state.mutatePost {
                        copy(
                            tripPlanViewState = TripPlanState.Content(
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
            TripDay("1", "Day 1", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."),
            TripDay("2", "Day 2", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source."),
            TripDay("3", "Day 3", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham."),
            TripDay("4","Day 4", "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text.")
        )
        return@withContext Result.Success(mockedTripPlan)
    }
}