package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.shops.ShopData
import com.example.apartmentinfoapp.domain.sights.SightsData

data class ShopState (
    val shopsInfoList: List<ShopData?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)