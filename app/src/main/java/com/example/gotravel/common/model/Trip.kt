package com.example.gotravel.common.model

import java.io.Serializable

data class Trip (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val numOfDays: String = "",
    val price: String = "",
    val photoSrc: Int,
    val travelMode: TravelMode
) : Serializable


enum class TravelMode {
    Bus,
    Airplane
}