package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsDataDto

data class AboutUsState(
    val aboutUsInfo: AboutUsDataDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)