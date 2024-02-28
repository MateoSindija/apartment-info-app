package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.apartmentinfoapp.data.mappers.toWeatherInfo
import com.example.apartmentinfoapp.data.remote.WeatherApi
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}