package com.example.apartmentinfoapp.data.interceptor

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenProvider @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun getAccessToken(): String {
        return sharedPreferences.getString("x-access-token", "") ?: ""
    }

    fun getApartmentId(): String {
        return sharedPreferences.getString("apartmentId", "") ?: ""
    }

    fun getOwnerId(): String {
        return sharedPreferences.getString("ownerId", "") ?: ""
    }

    fun getReservationId(): String {
        return sharedPreferences.getString("reservationId", "") ?: ""
    }

}