package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsDataDto
import com.example.apartmentinfoapp.domain.util.Resource

interface AboutUsRepository {
    suspend fun getAboutUs(apartmentId: String): Resource<AboutUsDataDto>
}