package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.domain.weather.WeatherInfo


interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}