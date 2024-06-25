package com.example.apartmentinfoapp.domain.beaches

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.weather.WeatherData
import kotlinx.parcelize.Parcelize

@Parcelize
data class BeachData(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imagesUrl: List<String> = emptyList(),
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val terrainType: String = "",
    var weatherData: WeatherData? = null,
    val titleImage: String? = null
) : Parcelable