package com.example.gotravel.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotravel.common.Result
import androidx.lifecycle.viewModelScope
import com.example.gotravel.R
import com.example.gotravel.common.model.TravelMode
import com.example.gotravel.common.model.Trip
import com.example.gotravel.utils.mutatePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {


    val state: MutableLiveData<HomeViewState> = MutableLiveData(HomeViewState())

    init {
        viewModelScope.launch {
            getTrips()
        }
    }

    private fun getTrips() {
        state.mutatePost { copy(tripsViewState = TripsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getTripsUseCase()) {
                is Result.Success -> {
                    state.mutatePost {
                        copy(
                            tripsViewState = TripsViewState.Content(
                                result.data
                            )
                        )
                    }
                }
            }
        }
    }


    // TODO: create useCase to get trips from api
    private suspend fun getTripsUseCase() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "",
                "Bali, Indonesia",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "aaaa",
                "ddd",
                "12.07.2020.",
                "19.07.2020",
                "7",
                "1000",
                R.drawable.img_dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "bbbb",
                "eee",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "12.12.2020.",
                "15.12.2020.",
                "3",
                "500",
                R.drawable.img_dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "25.12.2020.",
                "30.12.2020.",
                "5",
                "750",
                R.drawable.img_dummy,
                TravelMode.Airplane
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByLowestPrice() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "",
                "ccc",
                "fff",
                "12.12.2020.",
                "15.12.2020.",
                "3",
                "500",
                R.drawable.img_dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "25.12.2020.",
                "30.12.2020.",
                "5",
                "750",
                R.drawable.img_dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "aaaa",
                "ddd",
                "12.07.2020.",
                "19.07.2020",
                "7",
                "1000",
                R.drawable.img_dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "bbbb",
                "eee",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_dummy,
                TravelMode.Airplane
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByHighestPrice() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "",
                "ccc",
                "fff",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "bbbb",
                "eee",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "aaaa",
                "ddd",
                "12.07.2020.",
                "19.07.2020",
                "7",
                "1000",
                R.drawable.img_dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "25.12.2020.",
                "30.12.2020.",
                "5",
                "750",
                R.drawable.img_dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "12.12.2020.",
                "15.12.2020.",
                "3",
                "500",
                R.drawable.img_dummy,
                TravelMode.Airplane
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByPopularity() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "",
                "ccc",
                "fff",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "aaaa",
                "ddd",
                "12.07.2020.",
                "19.07.2020",
                "7",
                "1000",
                R.drawable.dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "bbbb",
                "eee",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.dummy,
                TravelMode.Bus
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "12.12.2020.",
                "15.12.2020.",
                "3",
                "500",
                R.drawable.dummy,
                TravelMode.Airplane
            ),
            Trip(
                "",
                "ccc",
                "fff",
                "25.12.2020.",
                "30.12.2020.",
                "5",
                "750",
                R.drawable.dummy,
                TravelMode.Airplane
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

}