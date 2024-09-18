package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.util.Resource


interface ReviewRepository {
    suspend fun submitReview(
        review: String?,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int,
        apartmentId: String,
    ): Resource<Boolean>

    suspend fun isReviewAlreadyIsSubmitted(apartmentId: String): Resource<Boolean>
}