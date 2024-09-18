package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.sights.SightDataDto
import com.example.apartmentinfoapp.domain.util.Resource

interface SightRepository {
    suspend fun getSights(apartmentId: String): Resource<List<SightDataDto>>
}