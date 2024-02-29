package com.example.apartmentinfoapp.data.remote

import com.squareup.moshi.Json
import java.util.Date


data class WeatherDataDto(
    val time: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "weather_code")
    val weatherCodes: List<Int>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>,
    @field:Json(name = "wind_speed_10m")
    val windSpeeds: List<Double>,
    @field:Json(name = "relative_humidity_2m")
    val humidities: List<Double>,
    @field:Json(name = "precipitation_probability")
    val precipitation: List<Int>,
    @field:Json(name = "visibility")
    val visibility: List<Double>,
    @field:Json(name = "wind_direction_10m")
    val windDirection: List<Int>,


)
