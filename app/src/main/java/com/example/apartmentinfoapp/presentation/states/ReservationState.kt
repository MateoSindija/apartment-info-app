package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.reservation.ReservationDataDto

data class ReservationState(
    val reservationInfo: ReservationDataDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
