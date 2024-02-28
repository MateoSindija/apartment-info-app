package com.example.apartmentinfoapp.data.remote

import com.squareup.moshi.Json

data class WeatherDto (
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto,
    @field:Json(name = "daily")
    val dailyLengthData: DailyLengthDataDto
)