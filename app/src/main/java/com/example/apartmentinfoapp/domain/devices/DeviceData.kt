package com.example.apartmentinfoapp.domain.devices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceData(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val titleImage: String? = null,
    val imagesUrl: List<String>? = null
) : Parcelable
