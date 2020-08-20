package com.example.gotravel.domain.repository

import com.example.gotravel.common.model.Trip
import com.example.gotravel.common.Result

interface ContentRepository {

    suspend fun getSavedTrips(): Result<List<Trip>>

}