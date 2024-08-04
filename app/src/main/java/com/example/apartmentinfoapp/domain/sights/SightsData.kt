package com.example.apartmentinfoapp.domain.sights

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.location.LocationDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SightDataDto(
    @field:Json(name = "sightId")
    val sightId: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "imagesUrl")
    val imagesUrl: List<String>,
    @field:Json(name = "location")
    val location: LocationDto,
    @field:Json(name = "titleImage")
    val titleImage: String
) : Parcelable
