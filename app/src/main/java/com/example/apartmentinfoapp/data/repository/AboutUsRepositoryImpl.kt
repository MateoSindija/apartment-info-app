package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.mappers.formatAboutUsImages
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.aboutUs.AboutUsDataDto
import com.example.apartmentinfoapp.domain.repository.AboutUsRepository
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class AboutUsRepositoryImpl @Inject constructor(private val api: ApartmentApi) : AboutUsRepository {
    override suspend fun getAboutUs(apartmentId: String): Resource<AboutUsDataDto> {
        return try {
            Resource.Success(data = api.getAboutUs(apartmentId).formatAboutUsImages())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}