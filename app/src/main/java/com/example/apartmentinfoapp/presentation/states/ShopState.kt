package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.shops.ShopDataDto

data class ShopState(
    val shopsInfoList: List<ShopDataDto?>? = null,
    val mineLat: Double? = null,
    val mineLng: Double? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)