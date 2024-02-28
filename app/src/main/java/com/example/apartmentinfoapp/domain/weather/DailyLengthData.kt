package com.example.apartmentinfoapp.domain.weather

import java.time.LocalDateTime

data class DailyLengthData(
    val time: String,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime
)
