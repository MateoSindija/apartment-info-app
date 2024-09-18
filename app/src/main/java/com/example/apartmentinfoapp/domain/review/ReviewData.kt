package com.example.apartmentinfoapp.domain.review

data class ReviewBody(
    val comfortRating: Int,
    val experienceRating: Int,
    val valueRating: Int,
    val apartmentId: String,
    val review: String?
)
