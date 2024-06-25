package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsData

data class AboutUsState(
    val aboutUsInfo: AboutUsData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)