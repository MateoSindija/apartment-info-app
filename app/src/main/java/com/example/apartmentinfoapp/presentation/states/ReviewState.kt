package com.example.apartmentinfoapp.presentation.states

data class ReviewState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ReviewExistanceState(
    var isReviewAlreadySubmitted: Boolean? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
