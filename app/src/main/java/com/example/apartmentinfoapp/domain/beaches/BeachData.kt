package com.example.apartmentinfoapp.domain.beaches

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.location.LocationDto
import com.example.apartmentinfoapp.domain.weather.WeatherData
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class BeachData(
    val beachId: String = "",
    val title: String = "",
    val description: String = "",
    val imagesUrl: List<String> = emptyList(),
    val location: LocationDto,
    val terrainType: String = "",
    var weatherData: WeatherData? = null,
    val titleImage: String? = null
) : Parcelable

@Parcelize
data class BeachDataDto(
    @field:Json(name = "beachId")
    val beachId: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "imagesUrl")
    val imagesUrl: List<String>,
    @field:Json(name = "location")
    val location: LocationDto,
    @field:Json(name = "terrainType")
    val terrainType: String,
    @field:Json(name = "titleImage")
    val titleImage: String,
    @field:Json(name = "updatedAt")
    val updatedAt: LocalDateTime,
    @field:Json(name = "createdAt")
    val createdAt: LocalDateTime,

    ) : Parcelable

