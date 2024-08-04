package com.example.apartmentinfoapp.domain.restaurants

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.location.LocationDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class RestaurantDataDto(
    @field:Json(name = "restaurantId")
    val restaurantId: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "review")
    val review: Double,
    @field:Json(name = "reviewAmount")
    val reviewAmount: Int,
    @field:Json(name = "phoneContact")
    val phoneContact: String,
    @field:Json(name = "emailContact")
    val emailContact: String,
    @field:Json(name = "imagesUrl")
    val imagesUrl: List<String>,
    @field:Json(name = "location")
    val location: LocationDto,
    @field:Json(name = "titleImage")
    val titleImage: String
) : Parcelable
