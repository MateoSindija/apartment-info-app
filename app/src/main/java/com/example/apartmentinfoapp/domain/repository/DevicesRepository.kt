package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.devices.DeviceData
import com.example.apartmentinfoapp.domain.util.Resource

interface DevicesRepository {
    suspend fun getDevicesList(): Resource<List<DeviceData>>
}