package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.mappers.formatImages
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.repository.SightRepository
import com.example.apartmentinfoapp.domain.sights.SightDataDto
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class SightRepositoryImpl @Inject constructor(private val api: ApartmentApi) : SightRepository {

    override suspend fun getSights(apartmentId: String): Resource<List<SightDataDto>> {
        return try {
            Resource.Success(data = api.getSights(apartmentId).formatImages())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}