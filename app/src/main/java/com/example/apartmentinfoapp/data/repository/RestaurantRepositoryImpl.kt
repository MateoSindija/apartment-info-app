package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.mappers.formatImages
import com.example.apartmentinfoapp.data.remote.ApartmentApi
import com.example.apartmentinfoapp.domain.repository.RestaurantRepository
import com.example.apartmentinfoapp.domain.restaurants.RestaurantDataDto
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(private val api: ApartmentApi) :
    RestaurantRepository {
    override suspend fun getRestaurantsList(apartmentId: String): Resource<List<RestaurantDataDto>> {
        return try {
            Resource.Success(data = api.getRestaurants(apartmentId).formatImages())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}
