package com.example.apartmentinfoapp.presentation

import com.example.apartmentinfoapp.domain.beaches.BeachData

data class BeachState(
    val beachInfo: List<BeachData?>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)