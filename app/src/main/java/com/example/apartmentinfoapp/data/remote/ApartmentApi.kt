package com.example.apartmentinfoapp.data.remote

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsDataDto
import com.example.apartmentinfoapp.domain.beaches.BeachDataDto
import com.example.apartmentinfoapp.domain.devices.DeviceDataDto
import com.example.apartmentinfoapp.domain.reservation.ReservationDataDto
import com.example.apartmentinfoapp.domain.restaurants.RestaurantDataDto
import com.example.apartmentinfoapp.domain.shops.ShopDataDto
import com.example.apartmentinfoapp.domain.sights.SightDataDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApartmentApi {
    @GET("apartment/{apartmentId}/beaches")
    suspend fun getBeaches(@Path("apartmentId") apartmentId: String): List<BeachDataDto>

    @GET("apartment/{apartmentId}/restaurants")
    suspend fun getRestaurants(@Path("apartmentId") apartmentId: String): List<RestaurantDataDto>

    @GET("apartment/{apartmentId}/sights")
    suspend fun getSights(@Path("apartmentId") apartmentId: String): List<SightDataDto>

    @GET("apartment/{apartmentId}/shops")
    suspend fun getShops(@Path("apartmentId") apartmentId: String): List<ShopDataDto>

    @GET("apartment/{apartmentId}/devices")
    suspend fun getDevices(@Path("apartmentId") apartmentId: String): List<DeviceDataDto>

    @GET("apartment/{apartmentId}/current-reservation")
    suspend fun getCurrentReservation(@Path("apartmentId") apartmentId: String): ReservationDataDto?

    @Multipart
    @POST("review/new")
    suspend fun postReview(
        @Part("review") review: RequestBody?,
        @Part("comfortRating") comfortRating: RequestBody,
        @Part("experienceRating") expRating: RequestBody,
        @Part("valueRating") valueRating: RequestBody,
        @Part("apartmentId") apartmentId: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Void

    @GET("review/{apartmentId}/status")
    suspend fun isReviewAlreadySubmitted(@Path("apartmentId") apartmentId: String): Boolean

    @GET("apartment/{apartmentId}/aboutUs")
    suspend fun getAboutUs(@Path("apartmentId") apartmentId: String): AboutUsDataDto
}