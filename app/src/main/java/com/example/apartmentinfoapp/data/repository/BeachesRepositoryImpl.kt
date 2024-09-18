package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.beaches.BeachDataDto
import com.example.apartmentinfoapp.domain.repository.BeachesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class BeachesRepositoryImpl @Inject constructor(private val api: ApartmentApi) : BeachesRepository {
    override suspend fun getBeachesList(
        apartmentId: String
    ): Resource<List<BeachDataDto>> {

        return try {
            Resource.Success(data = api.getBeaches(apartmentId))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}