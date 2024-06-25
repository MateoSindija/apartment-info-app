package com.example.apartmentinfoapp.domain.aboutUs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUsData(
    val id: String? = "",
    val moto: String? = "",
    val aboutUs: String? = ""
) : Parcelable
