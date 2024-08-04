package com.example.apartmentinfoapp.domain.location

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationDto(
    @field:Json(name = "coordinates")
    val coordinates: List<Double>,
    @field:Json(name = "crs")
    val crs: Crs,
    @field:Json(name = "type")
    val type: String
) : Parcelable {
    @Parcelize
    data class Crs(
        @field:Json(name = "properties")
        val properties: Properties,
        @field:Json(name = "type")
        val type: String
    ) : Parcelable {
        @Parcelize
        data class Properties(
            @field:Json(name = "name")
            val name: String
        ) : Parcelable
    }
}
