package com.example.apartmentinfoapp.domain.aboutUs

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUsDataDto(
    @field:Json(name = "moto")
    val moto: String? = "",
    @field:Json(name = "aboutUs")
    val aboutUs: String,
    @field:Json(name = "titleImage")
    val titleImage: String,
    @field:Json(name = "imagesUrl")
    val imagesUrl: List<String>
) : Parcelable
