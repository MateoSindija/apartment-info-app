package com.example.apartmentinfoapp.presentation.models

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.restaurants.RestaurantData
import com.example.apartmentinfoapp.presentation.states.BeachState
import com.example.apartmentinfoapp.presentation.states.RestaurantState
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


sealed class CommonDataState {
    data class BeachCardList(val beachState: BeachState?) : CommonDataState()
    data class RestaurantCardList(val restaurantState: RestaurantState?) : CommonDataState()
}

sealed class CommonCardData : Parcelable {
    @Parcelize
    data class BeachCard(val beachData: BeachData?, val mineLat: Double?, val mineLng: Double?) :
        CommonCardData()

    @Parcelize
    data class RestaurantCard(
        val restaurantData: RestaurantData?,
        val mineLat: Double?,
        val mineLng: Double?
    ) : CommonCardData()
}

