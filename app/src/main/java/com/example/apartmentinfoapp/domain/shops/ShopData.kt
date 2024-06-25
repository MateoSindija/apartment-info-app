package com.example.apartmentinfoapp.domain.shops

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopData(
    val description: String? = null,
    val title: String? = null,
    val imagesUrl: List<String>? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val id: String? = null,
    val titleImage: String? = null
) : Parcelable
