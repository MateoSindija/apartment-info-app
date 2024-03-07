package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.domain.weather.WeatherInfo

data class BeachState(
    val beachInfo: List<BeachData?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val weatherResult: Resource<List<WeatherInfo>>? = null

)