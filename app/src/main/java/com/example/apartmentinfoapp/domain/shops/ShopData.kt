package com.example.apartmentinfoapp.domain.shops

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.location.LocationDto
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopDataDto(
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "imagesUrl")
    val imagesUrl: List<String>,
    @field:Json(name = "location")
    val location: LocationDto,
    @field:Json(name = "shopId")
    val shopId: String,
    @field:Json(name = "titleImage")
    val titleImage: String,
) : Parcelable
