package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.restaurants.RestaurantData
import com.example.apartmentinfoapp.domain.sights.SightsData

data class SightsState(
    val sightsInfoList: List<SightsData?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
