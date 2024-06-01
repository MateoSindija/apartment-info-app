package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.shops.ShopData
import com.example.apartmentinfoapp.domain.util.Resource

interface ShopRepository {
    suspend fun getShopsList(lat: Double, lng: Double): Resource<List<ShopData>>
}