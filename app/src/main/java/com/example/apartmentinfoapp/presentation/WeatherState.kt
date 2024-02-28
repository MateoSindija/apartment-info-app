package com.example.apartmentinfoapp.presentation

import com.example.apartmentinfoapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
