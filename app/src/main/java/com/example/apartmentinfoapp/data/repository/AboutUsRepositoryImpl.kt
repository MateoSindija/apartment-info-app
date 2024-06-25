package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsData
import com.example.apartmentinfoapp.domain.repository.AboutUsRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AboutUsRepositoryImpl @Inject constructor() : AboutUsRepository {
    override suspend fun getAboutUs(): Resource<AboutUsData> {
        val fireStoreDatabase = FirebaseFirestore.getInstance()

        return try {
            val docSnapshot =
                fireStoreDatabase.collection("AboutUs").document("aboutUs").get().await()

            if (!docSnapshot.exists()) {
                Resource.Success(data = null)
            } else {

                var aboutUsData = docSnapshot.toObject(AboutUsData::class.java) ?: throw Exception(
                    "Error converting document to AboutUsData"
                )
                aboutUsData = aboutUsData.copy(id = docSnapshot.id)

                Resource.Success(data = aboutUsData)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}