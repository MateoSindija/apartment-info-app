package com.example.apartmentinfoapp.domain.weather

import com.example.apartmentinfoapp.data.mappers.IndexedDailyLengthData

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val currentDayLengthData: IndexedDailyLengthData?,
    val latitude: Double?,
    val longitude: Double?
)