package com.example.apartmentinfoapp.data.remote

import com.squareup.moshi.Json

data class DailyLengthDataDto(
    val time: List<String>,
    @field:Json(name ="sunrise")
    val sunrise: List<String>,
    @field:Json(name ="sunset")
    val sunset: List<String>,
)
