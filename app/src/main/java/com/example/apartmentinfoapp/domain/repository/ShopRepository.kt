package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.shops.ShopDataDto
import com.example.apartmentinfoapp.domain.util.Resource

interface ShopRepository {
    suspend fun getShopsList(apartmentId: String): Resource<List<ShopDataDto>>
}