package com.example.apartmentinfoapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.repository.BeachesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BeachesRepositoryImpl @Inject constructor() : BeachesRepository {
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getBeachesList(lat: Double, lng: Double): Resource<List<BeachData>> {
        return try {
            val snapshot = fireStoreDatabase.collection("Beaches").get().await()

            if(snapshot.isEmpty){
                Resource.Success(data = emptyList())
            }else {

                val listOfBeaches = snapshot.documents.map { document ->
                    val beachData = document.toObject(BeachData::class.java) ?: throw Exception("Error converting document to BeachData")
                    beachData.copy(id = document.id)
                }
                Resource.Success(data = listOfBeaches)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}