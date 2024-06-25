package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsData
import com.example.apartmentinfoapp.domain.util.Resource

interface AboutUsRepository {
    suspend fun getAboutUs(): Resource<AboutUsData>
}