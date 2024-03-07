package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.restaurants.RestaurantData
import com.example.apartmentinfoapp.domain.util.Resource

interface RestaurantRepository {
    suspend fun getRestaurantsList(lat: Double, lng: Double): Resource<List<RestaurantData>>
}