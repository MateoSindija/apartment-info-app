package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.sights.SightDataDto

data class SightsState(
    val sightsInfoList: List<SightDataDto?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
