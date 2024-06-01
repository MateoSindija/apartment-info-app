package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.domain.repository.ReviewRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor() : ReviewRepository {
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    override suspend fun submitReview(
        review: String,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int
    ): Resource<Boolean> {
        return try {
            val newReview = hashMapOf(
                "review" to review,
                "experienceRating" to expRating,
                "comfortRating" to comfortRating,
                "valueRating" to valueRating,
                "timestamp" to FieldValue.serverTimestamp()
            )
            fireStoreDatabase.collection("Reviews").add(newReview).await()
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}