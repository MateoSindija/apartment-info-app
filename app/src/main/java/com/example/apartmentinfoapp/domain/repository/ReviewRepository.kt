package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.util.Resource


interface ReviewRepository {
    suspend fun submitReview(
        review: String,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int,
    ): Resource<Boolean>
}