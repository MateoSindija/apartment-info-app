package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.sights.SightsData
import com.example.apartmentinfoapp.domain.util.Resource

interface SightRepository{
    suspend fun getSights(lat: Double, lng: Double): Resource<List<SightsData>>
}