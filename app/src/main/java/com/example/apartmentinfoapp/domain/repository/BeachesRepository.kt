package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.util.Resource

interface BeachesRepository {
    suspend fun getBeachesList(lat: Double, lng: Double): Resource<List<BeachData>>
}