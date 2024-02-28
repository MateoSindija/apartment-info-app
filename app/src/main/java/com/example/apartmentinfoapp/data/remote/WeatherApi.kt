package com.example.apartmentinfoapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast?hourly=temperature_2m,weather_code,relative_humidity_2m,wind_speed_10m,pressure_msl,precipitation_probability,wind_direction_10m,visibility&daily=sunrise,sunset&timezone=Europe%2FBerlin")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}