package com.example.apartmentinfoapp.domain.reservation

import com.squareup.moshi.Json
import java.time.LocalDateTime

data class ReservationDataDto(
    @field:Json(name = "reservationId")
    val reservationId: String,
    @field:Json(name = "startDate")
    val startDate: LocalDateTime,
    @field:Json(name = "endDate")
    val endDate: LocalDateTime,
    @field:Json(name = "clientName")
    val clientName: String
)
