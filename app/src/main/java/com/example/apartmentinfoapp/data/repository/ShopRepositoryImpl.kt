package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.domain.repository.ShopRepository
import com.example.apartmentinfoapp.domain.shops.ShopData
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor() : ShopRepository {
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    override suspend fun getShopsList(lat: Double, lng: Double): Resource<List<ShopData>> {
        return try {
            val snapshot = fireStoreDatabase.collection("Shops").get().await()

            if (snapshot.isEmpty) {
                Resource.Success(data = emptyList())
            } else {

                val listOfShops = snapshot.documents.map { document ->
                    val shopsData = document.toObject(ShopData::class.java)
                        ?: throw Exception("Error converting document to ShopData")
                    shopsData.copy(id = document.id)
                }
                Resource.Success(data = listOfShops)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}