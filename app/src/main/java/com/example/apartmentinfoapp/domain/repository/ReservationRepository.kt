package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.reservation.ReservationDataDto
import com.example.apartmentinfoapp.domain.util.Resource


interface ReservationRepository {
    suspend fun getCurrentReservation(apartmentId: String): Resource<ReservationDataDto?>
}