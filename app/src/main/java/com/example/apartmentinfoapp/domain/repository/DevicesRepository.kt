package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.devices.DeviceDataDto
import com.example.apartmentinfoapp.domain.util.Resource

interface DevicesRepository {
    suspend fun getDevicesList(apartmentId: String): Resource<List<DeviceDataDto>>
}