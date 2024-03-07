package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.devices.DeviceData
import com.example.apartmentinfoapp.domain.restaurants.RestaurantData

data class RestaurantState (
    val restaurantsInfoList: List<RestaurantData?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)