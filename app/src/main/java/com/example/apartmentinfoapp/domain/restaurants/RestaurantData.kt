package com.example.apartmentinfoapp.domain.restaurants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contacts(
    val email: String? = null,
    val number: String? = null
) : Parcelable


@Parcelize
data class RestaurantData(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val review: Double? = null,
    val reviewAmount: Int? = null,
    val contacts: Contacts? = null,
    val imagesUrl: List<String>? = null,
    val lat: Double? = null,
    val lng: Double? = null,
) : Parcelable
