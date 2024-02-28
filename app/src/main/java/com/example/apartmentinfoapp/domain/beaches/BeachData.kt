package com.example.apartmentinfoapp.domain.beaches

data class BeachData(
    val id: String = "",
    val title: String = "",
    val description:String = "",
    val imagesUrl: List<String> = emptyList(),
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val terrainType: String = "",
)