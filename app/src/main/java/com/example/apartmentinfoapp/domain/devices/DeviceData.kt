package com.example.apartmentinfoapp.domain.devices

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceDataDto(
    @field:Json(name = "deviceId")
    val deviceId: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "titleImage")
    val titleImage: String,
    @field:Json(name = "imagesUrl")
    val imagesUrl: List<String>
) : Parcelable
