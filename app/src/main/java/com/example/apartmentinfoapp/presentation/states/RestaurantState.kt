package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.restaurants.RestaurantDataDto

data class RestaurantState(
    val restaurantsInfoList: List<RestaurantDataDto?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)