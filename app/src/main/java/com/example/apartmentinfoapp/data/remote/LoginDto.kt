package com.example.apartmentinfoapp.data.remote

import com.squareup.moshi.Json

data class LoginDto(
    @field:Json(name = "token")
    val accessToken: String,
    @field:Json(name = "type")
    val type: String,
    @field:Json(name = "ownerId")
    val ownerId: String,
    @field:Json(name = "apartmentId")
    val apartmentId: String,
)
