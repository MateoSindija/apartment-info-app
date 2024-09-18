package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.devices.DeviceDataDto

data class DevicesListState(
    val devicesInfoList: List<DeviceDataDto>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
