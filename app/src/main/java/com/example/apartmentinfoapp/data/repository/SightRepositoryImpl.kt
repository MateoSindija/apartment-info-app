package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.domain.repository.SightRepository
import com.example.apartmentinfoapp.domain.sights.SightsData
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SightRepositoryImpl @Inject constructor() : SightRepository {
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    override suspend fun getSights(lat: Double, lng: Double): Resource<List<SightsData>> {
        return try {
            val snapshot = fireStoreDatabase.collection("Attractions").get().await()

            if (snapshot.isEmpty) {
                Resource.Success(data = emptyList())
            } else {

                val listOfSights = snapshot.documents.map { document ->
                    val sightsData = document.toObject(SightsData::class.java)
                        ?: throw Exception("Error converting document to SightsData")
                    sightsData.copy(id = document.id)
                }
                Resource.Success(data = listOfSights)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}