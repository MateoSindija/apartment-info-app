package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.restaurants.RestaurantDataDto
import com.example.apartmentinfoapp.domain.util.Resource

interface RestaurantRepository {
    suspend fun getRestaurantsList(apartmentId: String): Resource<List<RestaurantDataDto>>
}