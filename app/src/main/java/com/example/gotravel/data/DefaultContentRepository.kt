package com.example.gotravel.data

import com.example.gotravel.R
import com.example.gotravel.common.Result
import com.example.gotravel.common.model.TravelMode
import com.example.gotravel.common.model.Trip
import com.example.gotravel.domain.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultContentRepository: ContentRepository {

    override suspend fun getSavedTrips() = withContext(Dispatchers.IO) {
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
                    R.drawable.img_london_6,
                    R.drawable.img_london_2,
                    R.drawable.img_london_3,
                    R.drawable.img_london_4,
                    R.drawable.img_london_5,
                    R.drawable.img_london_1
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
            )
        )
        return@withContext Result.Success(mockedTrips)
    }
}