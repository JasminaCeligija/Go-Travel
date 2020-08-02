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
            refreshData()
        }
    }

    fun sortBy(sortOption: String) {

        viewModelScope.launch {
            when (sortOption) {
                "Price - Low to High" -> {
                    getTripsLowestPriceFirst()
                }
                "Price - High to Low" -> {
                    getTripsHighestPriceFirst()
                }
                "Time - Newest" -> {
                    getTripsNewestFirst()
                }
                "Time - Oldest" -> {
                    getTripsOldestFirst()
                }
                "Popularity" -> {
                    getTripsPopularFirst()
                }
                else -> {
                    getTripsPopularFirst()
                }
            }
        }
    }

    fun searchTripByName(name: String, trips: List<Trip>) {
        if(name.isBlank()) {
            viewModelScope.launch {
                getTripsPopularFirst()
            }
        } else {
            val newList = mutableListOf<Trip>()
            for(trip in trips) {
                if(trip.name.contains(name, ignoreCase = true)) {
                   newList.add(trip)
                }
            }
            state.mutatePost {
                copy(
                    tripsViewState = TripsViewState.Content(
                        newList as List<Trip>
                    )
                )
            }
        }
    }

    fun refreshData() {
        getTripsPopularFirst()
    }

    private fun getTripsPopularFirst() {
        state.mutatePost { copy(tripsViewState = TripsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getSortedTripsByPopularityUseCase()) {
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

    private fun getTripsLowestPriceFirst() {
        state.mutatePost { copy(tripsViewState = TripsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getSortedTripsByLowestPriceUseCase()) {
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

    private fun getTripsHighestPriceFirst() {
        state.mutatePost { copy(tripsViewState = TripsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getSortedTripsByHighestPriceUseCase()) {
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

    private fun getTripsNewestFirst() {
        state.mutatePost { copy(tripsViewState = TripsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getSortedTripsByTimeNewestUseCase()) {
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

    private fun getTripsOldestFirst() {
        state.mutatePost { copy(tripsViewState = TripsViewState.Loading) }
        viewModelScope.launch {
            when (val result = getSortedTripsByTimeOldestUseCase()) {
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

    // TODO: get all data from api
    private suspend fun getSortedTripsByLowestPriceUseCase() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "2",
                "Istanbul, Turkey",
                "Spring in Istanbul and Istanbul Tulip Festival 2020",
                "Istanbul is a dream destination, it’s located in the edge of Europe where east meets west. Istanbul is one of the truly great romantic cities, straddling the Bosphorus with its skyline studded with domes and minarets. Today it hums as Turkey's cultural heart and good-time capital. Its history tracks back from Byzantium to Constantinople to its place at the head of the Ottoman Empire." +
                        "In this city you can tramp the streets where crusaders and janissaries once marched; admire mosques that are the most sublime architectural expressions of Islamic piety; peer into the sultan's harem; and hunt for bargains in the Grand Bazaar. Side by side with Old Istanbul you'll find hip bars and clubs, flashy executives, malls and haute cuisine. " +
                        "All this history, hustle and bustle coexists around the spine of the Bosphorus, a constantly busy, heaving mass, dotted with ships and ferries, which provides the link between Europe and Asia. There is a rich arts culture, opera, music, cinema, which nods its head to the Ottoman and Byzantine ways while taking cues from Europe. " +
                        "Istanbul has architecture, art, nightlife, cuisine, history, religion, shopping more than enough of it, at prices which are among the lowest in Europe.",
                "14.04.2020.",
                "17.04.2020",
                "4",
                "750",
                R.drawable.img_istanbul_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_istanbul_0,
                    R.drawable.img_istanbul_1,
                    R.drawable.img_istanbul_2,
                    R.drawable.img_istanbul_3,
                    R.drawable.img_istanbul_4,
                    R.drawable.img_istanbul_5,
                    R.drawable.img_istanbul_6
                )
            ),
            Trip(
                "5",
                "London, England",
                "Limited number of beds",
                "London captivates all, bringing culture, light, colour, diversity and fun to all those that visit the city. It is capable of surprising not only first-timers, but also those who return time and time again to discover its endless hidden treasures.",
                "07.06.2020.",
                "10.16.2020.",
                "4",
                "1000",
                R.drawable.img_london_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_london_0,
                    R.drawable.img_london_1,
                    R.drawable.img_london_2,
                    R.drawable.img_london_3,
                    R.drawable.img_london_4,
                    R.drawable.img_london_5,
                    R.drawable.img_london_6
                )
            ),
            Trip(
                "3",
                "Amsterdam, Netherlands",
                "Last minute deal",
                "Amsterdam is one of the greatest small cities in the world. From Amsterdam canals to world-famous Amsterdam museums and historical Amsterdam sights, it is one of the most romantic and beautiful cities in Europe. Canal cruises are a popular way to see the city from the perspective of its canals. " +
                        "Amsterdam is also a city of tolerance and diversity. It has all the advantages of a big city: rich culture, lively Amsterdam nightlife, international restaurants, good transport - but is quiet, and largely thanks to its extensive canals, has a little road traffic. In this city your destination is never far away, but get a bike for an authentic local experience.",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2000",
                R.drawable.img_amsterdam_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_amsterdam_0,
                    R.drawable.img_amsterdam_1,
                    R.drawable.img_amsterdam_2,
                    R.drawable.img_amsterdam_3,
                    R.drawable.img_amsterdam_4,
                    R.drawable.img_amsterdam_5,
                    R.drawable.img_amsterdam_6
                )
            ),
            Trip(
                "4",
                "Moscow, Russia",
                "Let's explore Moscow",
                "As one of the most vibrant European capitals, Moscow is a powerful mix of history and edginess, full of world-famous sites and attractions." +
                        " Russia’s capital has been in existence for more than 800 years and has enough to keep visitors busy for months. Here’s the ultimate first-timer’s list of things to do in Moscow, from Europe’s oldest fortress and grandiose cathedrals to lively green spaces and futuristic skyscrapers.",
                "15.12.2020.",
                "20.12.2020.",
                "15",
                "2500",
                R.drawable.img_moscow_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_moscow_0,
                    R.drawable.img_moscow_1,
                    R.drawable.img_moscow_2,
                    R.drawable.img_moscow_3,
                    R.drawable.img_moscow_4,
                    R.drawable.img_moscow_5,
                    R.drawable.img_moscow_6
                )
            ),
            Trip(
                "1",
                "Bali, Indonesia",
                "Limited number of beds",
                "Bali is a favourite destination for many people around the world and it's easy to see why. From its list of unending idyllic beaches, captivating  spiritual energy, terraced rice fields, and exotic sunsets. There are thousands of gift shops in Bali." +
                        " From Denpasar to Ubud, you'll find many things you'd like to bring back home. Balinese signature souvenir range from delicious snacks and beverages, to the exquisite handcrafted goods like Tenun Bali. " +
                        "As Bali is located 8 degrees south of the equator, you will find the climate to be the typical tropical, warm and humid all year round with two main distinctive seasons: Dry Season and Rainy Season. Some of the areas around Bali's central mountains (volcanoes) have several peaks over 3,000 meters above sea levels. Up here the temperatures are considerably cooler, and there is much more rainfall than in the coastal areas.",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_bali_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_bali_1,
                    R.drawable.img_bali_2,
                    R.drawable.img_bali_3,
                    R.drawable.img_bali_4,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy
                )
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByHighestPriceUseCase() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "1",
                "Bali, Indonesia",
                "Limited number of beds",
                "Bali is a favourite destination for many people around the world and it's easy to see why. From its list of unending idyllic beaches, captivating  spiritual energy, terraced rice fields, and exotic sunsets. There are thousands of gift shops in Bali." +
                        " From Denpasar to Ubud, you'll find many things you'd like to bring back home. Balinese signature souvenir range from delicious snacks and beverages, to the exquisite handcrafted goods like Tenun Bali. " +
                        "As Bali is located 8 degrees south of the equator, you will find the climate to be the typical tropical, warm and humid all year round with two main distinctive seasons: Dry Season and Rainy Season. Some of the areas around Bali's central mountains (volcanoes) have several peaks over 3,000 meters above sea levels. Up here the temperatures are considerably cooler, and there is much more rainfall than in the coastal areas.",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_bali_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_bali_1,
                    R.drawable.img_bali_2,
                    R.drawable.img_bali_3,
                    R.drawable.img_bali_4,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy
                )
            ),
            Trip(
                "3",
                "Amsterdam, Netherlands",
                "Last minute deal",
                "Amsterdam is one of the greatest small cities in the world. From Amsterdam canals to world-famous Amsterdam museums and historical Amsterdam sights, it is one of the most romantic and beautiful cities in Europe. Canal cruises are a popular way to see the city from the perspective of its canals. " +
                        "Amsterdam is also a city of tolerance and diversity. It has all the advantages of a big city: rich culture, lively Amsterdam nightlife, international restaurants, good transport - but is quiet, and largely thanks to its extensive canals, has a little road traffic. In this city your destination is never far away, but get a bike for an authentic local experience.",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_amsterdam_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_amsterdam_0,
                    R.drawable.img_amsterdam_1,
                    R.drawable.img_amsterdam_2,
                    R.drawable.img_amsterdam_3,
                    R.drawable.img_amsterdam_4,
                    R.drawable.img_amsterdam_5,
                    R.drawable.img_amsterdam_6
                )
            ),
            Trip(
                "4",
                "Moscow, Russia",
                "Let's explore Moscow",
                "As one of the most vibrant European capitals, Moscow is a powerful mix of history and edginess, full of world-famous sites and attractions." +
                        " Russia’s capital has been in existence for more than 800 years and has enough to keep visitors busy for months. Here’s the ultimate first-timer’s list of things to do in Moscow, from Europe’s oldest fortress and grandiose cathedrals to lively green spaces and futuristic skyscrapers.",
                "15.12.2020.",
                "20.12.2020.",
                "15",
                "2000",
                R.drawable.img_moscow_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_moscow_0,
                    R.drawable.img_moscow_1,
                    R.drawable.img_moscow_2,
                    R.drawable.img_moscow_3,
                    R.drawable.img_moscow_4,
                    R.drawable.img_moscow_5,
                    R.drawable.img_moscow_6
                )
            ),
            Trip(
                "5",
                "London, England",
                "Limited number of beds",
                "London captivates all, bringing culture, light, colour, diversity and fun to all those that visit the city. It is capable of surprising not only first-timers, but also those who return time and time again to discover its endless hidden treasures.",
                "07.06.2020.",
                "10.16.2020.",
                "4",
                "1000",
                R.drawable.img_london_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_london_0,
                    R.drawable.img_london_1,
                    R.drawable.img_london_2,
                    R.drawable.img_london_3,
                    R.drawable.img_london_4,
                    R.drawable.img_london_5,
                    R.drawable.img_london_6
                )
            ),
            Trip(
                "2",
                "Istanbul, Turkey",
                "Spring in Istanbul and Istanbul Tulip Festival 2020",
                "Istanbul is a dream destination, it’s located in the edge of Europe where east meets west. Istanbul is one of the truly great romantic cities, straddling the Bosphorus with its skyline studded with domes and minarets. Today it hums as Turkey's cultural heart and good-time capital. Its history tracks back from Byzantium to Constantinople to its place at the head of the Ottoman Empire." +
                        "In this city you can tramp the streets where crusaders and janissaries once marched; admire mosques that are the most sublime architectural expressions of Islamic piety; peer into the sultan's harem; and hunt for bargains in the Grand Bazaar. Side by side with Old Istanbul you'll find hip bars and clubs, flashy executives, malls and haute cuisine. " +
                        "All this history, hustle and bustle coexists around the spine of the Bosphorus, a constantly busy, heaving mass, dotted with ships and ferries, which provides the link between Europe and Asia. There is a rich arts culture, opera, music, cinema, which nods its head to the Ottoman and Byzantine ways while taking cues from Europe. " +
                        "Istanbul has architecture, art, nightlife, cuisine, history, religion, shopping more than enough of it, at prices which are among the lowest in Europe.",
                "14.04.2020.",
                "17.04.2020",
                "4",
                "750",
                R.drawable.img_istanbul_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_istanbul_0,
                    R.drawable.img_istanbul_1,
                    R.drawable.img_istanbul_2,
                    R.drawable.img_istanbul_3,
                    R.drawable.img_istanbul_4,
                    R.drawable.img_istanbul_5,
                    R.drawable.img_istanbul_6
                )
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByTimeNewestUseCase() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "4",
                "Moscow, Russia",
                "Let's explore Moscow",
                "As one of the most vibrant European capitals, Moscow is a powerful mix of history and edginess, full of world-famous sites and attractions." +
                        " Russia’s capital has been in existence for more than 800 years and has enough to keep visitors busy for months. Here’s the ultimate first-timer’s list of things to do in Moscow, from Europe’s oldest fortress and grandiose cathedrals to lively green spaces and futuristic skyscrapers.",
                "15.12.2020.",
                "20.12.2020.",
                "15",
                "2000",
                R.drawable.img_moscow_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_moscow_0,
                    R.drawable.img_moscow_1,
                    R.drawable.img_moscow_2,
                    R.drawable.img_moscow_3,
                    R.drawable.img_moscow_4,
                    R.drawable.img_moscow_5,
                    R.drawable.img_moscow_6
                )
            ),
            Trip(
                "2",
                "Istanbul, Turkey",
                "Spring in Istanbul and Istanbul Tulip Festival 2020",
                "Istanbul is a dream destination, it’s located in the edge of Europe where east meets west. Istanbul is one of the truly great romantic cities, straddling the Bosphorus with its skyline studded with domes and minarets. Today it hums as Turkey's cultural heart and good-time capital. Its history tracks back from Byzantium to Constantinople to its place at the head of the Ottoman Empire." +
                        "In this city you can tramp the streets where crusaders and janissaries once marched; admire mosques that are the most sublime architectural expressions of Islamic piety; peer into the sultan's harem; and hunt for bargains in the Grand Bazaar. Side by side with Old Istanbul you'll find hip bars and clubs, flashy executives, malls and haute cuisine. " +
                        "All this history, hustle and bustle coexists around the spine of the Bosphorus, a constantly busy, heaving mass, dotted with ships and ferries, which provides the link between Europe and Asia. There is a rich arts culture, opera, music, cinema, which nods its head to the Ottoman and Byzantine ways while taking cues from Europe. " +
                        "Istanbul has architecture, art, nightlife, cuisine, history, religion, shopping more than enough of it, at prices which are among the lowest in Europe.",
                "14.04.2020.",
                "17.04.2020",
                "4",
                "750",
                R.drawable.img_istanbul_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_istanbul_0,
                    R.drawable.img_istanbul_1,
                    R.drawable.img_istanbul_2,
                    R.drawable.img_istanbul_3,
                    R.drawable.img_istanbul_4,
                    R.drawable.img_istanbul_5,
                    R.drawable.img_istanbul_6
                )
            ),
            Trip(
                "3",
                "Amsterdam, Netherlands",
                "Last minute deal",
                "Amsterdam is one of the greatest small cities in the world. From Amsterdam canals to world-famous Amsterdam museums and historical Amsterdam sights, it is one of the most romantic and beautiful cities in Europe. Canal cruises are a popular way to see the city from the perspective of its canals. " +
                        "Amsterdam is also a city of tolerance and diversity. It has all the advantages of a big city: rich culture, lively Amsterdam nightlife, international restaurants, good transport - but is quiet, and largely thanks to its extensive canals, has a little road traffic. In this city your destination is never far away, but get a bike for an authentic local experience.",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_amsterdam_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_amsterdam_0,
                    R.drawable.img_amsterdam_1,
                    R.drawable.img_amsterdam_2,
                    R.drawable.img_amsterdam_3,
                    R.drawable.img_amsterdam_4,
                    R.drawable.img_amsterdam_5,
                    R.drawable.img_amsterdam_6
                )
            ),
            Trip(
                "1",
                "Bali, Indonesia",
                "Limited number of beds",
                "Bali is a favourite destination for many people around the world and it's easy to see why. From its list of unending idyllic beaches, captivating  spiritual energy, terraced rice fields, and exotic sunsets. There are thousands of gift shops in Bali." +
                        " From Denpasar to Ubud, you'll find many things you'd like to bring back home. Balinese signature souvenir range from delicious snacks and beverages, to the exquisite handcrafted goods like Tenun Bali. " +
                        "As Bali is located 8 degrees south of the equator, you will find the climate to be the typical tropical, warm and humid all year round with two main distinctive seasons: Dry Season and Rainy Season. Some of the areas around Bali's central mountains (volcanoes) have several peaks over 3,000 meters above sea levels. Up here the temperatures are considerably cooler, and there is much more rainfall than in the coastal areas.",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_bali_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_bali_1,
                    R.drawable.img_bali_2,
                    R.drawable.img_bali_3,
                    R.drawable.img_bali_4,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy
                )
            ),
            Trip(
                "5",
                "London, England",
                "Limited number of beds",
                "London captivates all, bringing culture, light, colour, diversity and fun to all those that visit the city. It is capable of surprising not only first-timers, but also those who return time and time again to discover its endless hidden treasures.",
                "07.06.2020.",
                "10.16.2020.",
                "4",
                "1000",
                R.drawable.img_london_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_london_0,
                    R.drawable.img_london_1,
                    R.drawable.img_london_2,
                    R.drawable.img_london_3,
                    R.drawable.img_london_4,
                    R.drawable.img_london_5,
                    R.drawable.img_london_6
                )
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByTimeOldestUseCase() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "5",
                "London, England",
                "Limited number of beds",
                "London captivates all, bringing culture, light, colour, diversity and fun to all those that visit the city. It is capable of surprising not only first-timers, but also those who return time and time again to discover its endless hidden treasures.",
                "07.06.2020.",
                "10.16.2020.",
                "4",
                "1000",
                R.drawable.img_london_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_london_0,
                    R.drawable.img_london_1,
                    R.drawable.img_london_2,
                    R.drawable.img_london_3,
                    R.drawable.img_london_4,
                    R.drawable.img_london_5,
                    R.drawable.img_london_6
                )
            ),
            Trip(
                "3",
                "Amsterdam, Netherlands",
                "Last minute deal",
                "Amsterdam is one of the greatest small cities in the world. From Amsterdam canals to world-famous Amsterdam museums and historical Amsterdam sights, it is one of the most romantic and beautiful cities in Europe. Canal cruises are a popular way to see the city from the perspective of its canals. " +
                        "Amsterdam is also a city of tolerance and diversity. It has all the advantages of a big city: rich culture, lively Amsterdam nightlife, international restaurants, good transport - but is quiet, and largely thanks to its extensive canals, has a little road traffic. In this city your destination is never far away, but get a bike for an authentic local experience.",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_amsterdam_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_amsterdam_0,
                    R.drawable.img_amsterdam_1,
                    R.drawable.img_amsterdam_2,
                    R.drawable.img_amsterdam_3,
                    R.drawable.img_amsterdam_4,
                    R.drawable.img_amsterdam_5,
                    R.drawable.img_amsterdam_6
                )
            ),
            Trip(
                "4",
                "Moscow, Russia",
                "Let's explore Moscow",
                "As one of the most vibrant European capitals, Moscow is a powerful mix of history and edginess, full of world-famous sites and attractions." +
                        " Russia’s capital has been in existence for more than 800 years and has enough to keep visitors busy for months. Here’s the ultimate first-timer’s list of things to do in Moscow, from Europe’s oldest fortress and grandiose cathedrals to lively green spaces and futuristic skyscrapers.",
                "15.12.2020.",
                "20.12.2020.",
                "15",
                "2000",
                R.drawable.img_moscow_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_moscow_0,
                    R.drawable.img_moscow_1,
                    R.drawable.img_moscow_2,
                    R.drawable.img_moscow_3,
                    R.drawable.img_moscow_4,
                    R.drawable.img_moscow_5,
                    R.drawable.img_moscow_6
                )
            ),
            Trip(
                "2",
                "Istanbul, Turkey",
                "Spring in Istanbul and Istanbul Tulip Festival 2020",
                "Istanbul is a dream destination, it’s located in the edge of Europe where east meets west. Istanbul is one of the truly great romantic cities, straddling the Bosphorus with its skyline studded with domes and minarets. Today it hums as Turkey's cultural heart and good-time capital. Its history tracks back from Byzantium to Constantinople to its place at the head of the Ottoman Empire." +
                        "In this city you can tramp the streets where crusaders and janissaries once marched; admire mosques that are the most sublime architectural expressions of Islamic piety; peer into the sultan's harem; and hunt for bargains in the Grand Bazaar. Side by side with Old Istanbul you'll find hip bars and clubs, flashy executives, malls and haute cuisine. " +
                        "All this history, hustle and bustle coexists around the spine of the Bosphorus, a constantly busy, heaving mass, dotted with ships and ferries, which provides the link between Europe and Asia. There is a rich arts culture, opera, music, cinema, which nods its head to the Ottoman and Byzantine ways while taking cues from Europe. " +
                        "Istanbul has architecture, art, nightlife, cuisine, history, religion, shopping more than enough of it, at prices which are among the lowest in Europe.",
                "14.04.2020.",
                "17.04.2020",
                "4",
                "750",
                R.drawable.img_istanbul_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_istanbul_0,
                    R.drawable.img_istanbul_1,
                    R.drawable.img_istanbul_2,
                    R.drawable.img_istanbul_3,
                    R.drawable.img_istanbul_4,
                    R.drawable.img_istanbul_5,
                    R.drawable.img_istanbul_6
                )
            ),
            Trip(
                "1",
                "Bali, Indonesia",
                "Limited number of beds",
                "Bali is a favourite destination for many people around the world and it's easy to see why. From its list of unending idyllic beaches, captivating  spiritual energy, terraced rice fields, and exotic sunsets. There are thousands of gift shops in Bali." +
                        " From Denpasar to Ubud, you'll find many things you'd like to bring back home. Balinese signature souvenir range from delicious snacks and beverages, to the exquisite handcrafted goods like Tenun Bali. " +
                        "As Bali is located 8 degrees south of the equator, you will find the climate to be the typical tropical, warm and humid all year round with two main distinctive seasons: Dry Season and Rainy Season. Some of the areas around Bali's central mountains (volcanoes) have several peaks over 3,000 meters above sea levels. Up here the temperatures are considerably cooler, and there is much more rainfall than in the coastal areas.",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_bali_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_bali_1,
                    R.drawable.img_bali_2,
                    R.drawable.img_bali_3,
                    R.drawable.img_bali_4,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy
                )
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

    private suspend fun getSortedTripsByPopularityUseCase() = withContext(Dispatchers.IO) {
        val mockedTrips = listOf(
            Trip(
                "1",
                "Bali, Indonesia",
                "Limited number of beds",
                "Bali is a favourite destination for many people around the world and it's easy to see why. From its list of unending idyllic beaches, captivating  spiritual energy, terraced rice fields, and exotic sunsets. There are thousands of gift shops in Bali." +
                        " From Denpasar to Ubud, you'll find many things you'd like to bring back home. Balinese signature souvenir range from delicious snacks and beverages, to the exquisite handcrafted goods like Tenun Bali. " +
                        "As Bali is located 8 degrees south of the equator, you will find the climate to be the typical tropical, warm and humid all year round with two main distinctive seasons: Dry Season and Rainy Season. Some of the areas around Bali's central mountains (volcanoes) have several peaks over 3,000 meters above sea levels. Up here the temperatures are considerably cooler, and there is much more rainfall than in the coastal areas.",
                "14.08.2020.",
                "26.08.2020.",
                "12",
                "3115",
                R.drawable.img_bali_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_bali_1,
                    R.drawable.img_bali_2,
                    R.drawable.img_bali_3,
                    R.drawable.img_bali_4,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy,
                    R.drawable.img_dummy
                )
            ),
            Trip(
                "2",
                "Istanbul, Turkey",
                "Spring in Istanbul and Istanbul Tulip Festival 2020",
                "Istanbul is a dream destination, it’s located in the edge of Europe where east meets west. Istanbul is one of the truly great romantic cities, straddling the Bosphorus with its skyline studded with domes and minarets. Today it hums as Turkey's cultural heart and good-time capital. Its history tracks back from Byzantium to Constantinople to its place at the head of the Ottoman Empire." +
                        "In this city you can tramp the streets where crusaders and janissaries once marched; admire mosques that are the most sublime architectural expressions of Islamic piety; peer into the sultan's harem; and hunt for bargains in the Grand Bazaar. Side by side with Old Istanbul you'll find hip bars and clubs, flashy executives, malls and haute cuisine. " +
                        "All this history, hustle and bustle coexists around the spine of the Bosphorus, a constantly busy, heaving mass, dotted with ships and ferries, which provides the link between Europe and Asia. There is a rich arts culture, opera, music, cinema, which nods its head to the Ottoman and Byzantine ways while taking cues from Europe. " +
                        "Istanbul has architecture, art, nightlife, cuisine, history, religion, shopping more than enough of it, at prices which are among the lowest in Europe.",
                "14.04.2020.",
                "17.04.2020",
                "4",
                "750",
                R.drawable.img_istanbul_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_istanbul_0,
                    R.drawable.img_istanbul_1,
                    R.drawable.img_istanbul_2,
                    R.drawable.img_istanbul_3,
                    R.drawable.img_istanbul_4,
                    R.drawable.img_istanbul_5,
                    R.drawable.img_istanbul_6
                )
            ),
            Trip(
                "4",
                "Moscow, Russia",
                "Let's explore Moscow",
                "As one of the most vibrant European capitals, Moscow is a powerful mix of history and edginess, full of world-famous sites and attractions." +
                        " Russia’s capital has been in existence for more than 800 years and has enough to keep visitors busy for months. Here’s the ultimate first-timer’s list of things to do in Moscow, from Europe’s oldest fortress and grandiose cathedrals to lively green spaces and futuristic skyscrapers.",
                "15.12.2020.",
                "20.12.2020.",
                "15",
                "2000",
                R.drawable.img_moscow_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_moscow_0,
                    R.drawable.img_moscow_1,
                    R.drawable.img_moscow_2,
                    R.drawable.img_moscow_3,
                    R.drawable.img_moscow_4,
                    R.drawable.img_moscow_5,
                    R.drawable.img_moscow_6
                )
            ),
            Trip(
                "3",
                "Amsterdam, Netherlands",
                "Last minute deal",
                "Amsterdam is one of the greatest small cities in the world. From Amsterdam canals to world-famous Amsterdam museums and historical Amsterdam sights, it is one of the most romantic and beautiful cities in Europe. Canal cruises are a popular way to see the city from the perspective of its canals. " +
                        "Amsterdam is also a city of tolerance and diversity. It has all the advantages of a big city: rich culture, lively Amsterdam nightlife, international restaurants, good transport - but is quiet, and largely thanks to its extensive canals, has a little road traffic. In this city your destination is never far away, but get a bike for an authentic local experience.",
                "10.11.2020.",
                "20.11.2020.",
                "10",
                "2500",
                R.drawable.img_amsterdam_main,
                TravelMode.Bus,
                listOf(
                    R.drawable.img_amsterdam_0,
                    R.drawable.img_amsterdam_1,
                    R.drawable.img_amsterdam_2,
                    R.drawable.img_amsterdam_3,
                    R.drawable.img_amsterdam_4,
                    R.drawable.img_amsterdam_5,
                    R.drawable.img_amsterdam_6
                )
            ),
            Trip(
                "5",
                "London, England",
                "Limited number of beds",
                "London captivates all, bringing culture, light, colour, diversity and fun to all those that visit the city. It is capable of surprising not only first-timers, but also those who return time and time again to discover its endless hidden treasures.",
                "07.06.2020.",
                "10.16.2020.",
                "4",
                "1000",
                R.drawable.img_london_main,
                TravelMode.Airplane,
                listOf(
                    R.drawable.img_london_0,
                    R.drawable.img_london_1,
                    R.drawable.img_london_2,
                    R.drawable.img_london_3,
                    R.drawable.img_london_4,
                    R.drawable.img_london_5,
                    R.drawable.img_london_6
                )
            )
        )
        return@withContext Result.Success(mockedTrips)
    }

}