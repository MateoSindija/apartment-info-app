package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.devices.DeviceData

data class DevicesListState(
    val devicesInfoList: List<DeviceData>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
