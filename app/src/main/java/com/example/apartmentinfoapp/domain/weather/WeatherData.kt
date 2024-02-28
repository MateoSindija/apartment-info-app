package com.example.apartmentinfoapp.domain.weather

import java.time.LocalDateTime

data class WeatherData (
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherType: WeatherType,
    val precipitation: Int,
    val windDirection: Int,
    val visibility: Double,
)