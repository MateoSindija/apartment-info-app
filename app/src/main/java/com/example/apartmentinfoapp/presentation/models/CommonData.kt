package com.example.apartmentinfoapp.presentation.models

import android.os.Parcelable
import com.example.apartmentinfoapp.domain.aboutUs.AboutUsData
import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.devices.DeviceData
import com.example.apartmentinfoapp.domain.restaurants.RestaurantData
import com.example.apartmentinfoapp.domain.shops.ShopData
import com.example.apartmentinfoapp.domain.sights.SightsData
import com.example.apartmentinfoapp.presentation.states.AboutUsState
import com.example.apartmentinfoapp.presentation.states.BeachState
import com.example.apartmentinfoapp.presentation.states.DevicesListState
import com.example.apartmentinfoapp.presentation.states.RestaurantState
import com.example.apartmentinfoapp.presentation.states.ShopState
import kotlinx.parcelize.Parcelize


sealed class CommonDataState {
    data class BeachCardList(val beachState: BeachState?) : CommonDataState()
    data class RestaurantCardList(val restaurantState: RestaurantState?) : CommonDataState()
    data class ShopCardList(val shopState: ShopState?) : CommonDataState()
    data class DevicesCardList(val deviceState: DevicesListState?) : CommonDataState()
    data class AboutUsCard(val aboutUsState: AboutUsState?) : CommonDataState()

    //  data class SightCardList(val sightsState: SightsState) : CommonDataState()
}

sealed class CommonCardData : Parcelable {
    @Parcelize
    data class BeachCard(val beachData: BeachData?, val mineLat: Double?, val mineLng: Double?) :
        CommonCardData()

    @Parcelize
    data class SightCard(val sightsData: SightsData?, val mineLat: Double?, val mineLng: Double?) :
        CommonCardData()

    @Parcelize
    data class ShopCard(val shopData: ShopData?, val mineLat: Double?, val mineLng: Double?) :
        CommonCardData()

    @Parcelize
    data class AboutUsCard(val aboutUsData: AboutUsData?) :
        CommonCardData()

    @Parcelize
    data class DeviceCard(val deviceData: DeviceData?) :
        CommonCardData()

    @Parcelize
    data class RestaurantCard(
        val restaurantData: RestaurantData?,
        val mineLat: Double?,
        val mineLng: Double?
    ) : CommonCardData()
}

