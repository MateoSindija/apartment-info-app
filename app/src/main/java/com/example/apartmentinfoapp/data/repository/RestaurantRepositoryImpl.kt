package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.remote.WeatherApi
import com.example.apartmentinfoapp.domain.devices.DeviceData
import com.example.apartmentinfoapp.domain.repository.RestaurantRepository
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import com.example.apartmentinfoapp.domain.restaurants.RestaurantData
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.LatLng
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor() : RestaurantRepository {
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    override suspend fun getRestaurantsList(lat: Double, lng: Double): Resource<List<RestaurantData>> {
        return try {
            val snapshot = fireStoreDatabase.collection("Restaurants").get().await()

            if (snapshot.isEmpty) {
                Resource.Success(data = emptyList())
            } else {

                val listOfRestaurants = snapshot.documents.map { document ->
                    val restaurantData = document.toObject(RestaurantData::class.java)
                        ?: throw Exception("Error converting document to DeviceData")
                    restaurantData.copy(id = document.id)
                }
                Resource.Success(data = listOfRestaurants)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}
