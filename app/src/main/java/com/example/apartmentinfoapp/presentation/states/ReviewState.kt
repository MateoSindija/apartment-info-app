package com.example.apartmentinfoapp.presentation.states

data class ReviewState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
