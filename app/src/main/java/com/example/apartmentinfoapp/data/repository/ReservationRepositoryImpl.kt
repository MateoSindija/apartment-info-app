package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.repository.ReservationRepository
import com.example.apartmentinfoapp.domain.reservation.ReservationDataDto
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(private val api: ApartmentApi) :
    ReservationRepository {
    override suspend fun getCurrentReservation(apartmentId: String): Resource<ReservationDataDto?> {
        return try {
            Resource.Success(data = api.getCurrentReservation(apartmentId))

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}