package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MultipleWeatherState(
    val weatherInfoList: List<WeatherInfo?>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)