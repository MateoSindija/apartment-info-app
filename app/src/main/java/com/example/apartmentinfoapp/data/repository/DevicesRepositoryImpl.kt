package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.mappers.formatImages
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.devices.DeviceDataDto
import com.example.apartmentinfoapp.domain.repository.DevicesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class DevicesRepositoryImpl @Inject constructor(private val api: ApartmentApi) : DevicesRepository {

    override suspend fun getDevicesList(apartmentId: String): Resource<List<DeviceDataDto>> {
        return try {
            Resource.Success(data = api.getDevices(apartmentId).formatImages())

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}