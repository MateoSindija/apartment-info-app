package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.domain.devices.DeviceData
import com.example.apartmentinfoapp.domain.repository.DevicesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DevicesRepositoryImpl @Inject constructor() : DevicesRepository {

    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    override suspend fun getDevicesList(): Resource<List<DeviceData>> {
        return try {
            val snapshot = fireStoreDatabase.collection("Devices").get().await()

            if (snapshot.isEmpty) {
                Resource.Success(data = emptyList())
            } else {

                val listOfDevices = snapshot.documents.map { document ->
                    val deviceData = document.toObject(DeviceData::class.java)
                        ?: throw Exception("Error converting document to DeviceData")
                    deviceData.copy(id = document.id)
                }
                Resource.Success(data = listOfDevices)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}