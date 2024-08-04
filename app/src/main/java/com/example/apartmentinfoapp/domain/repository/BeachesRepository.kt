package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.beaches.BeachDataDto
import com.example.apartmentinfoapp.domain.util.Resource

interface BeachesRepository {
    suspend fun getBeachesList(
        apartmentId: String
    ): Resource<List<BeachDataDto>>
}