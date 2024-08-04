package com.example.apartmentinfoapp.data.mappers

import com.example.apartmentinfoapp.domain.aboutUs.AboutUsDataDto
import com.example.apartmentinfoapp.domain.devices.DeviceDataDto
import com.example.apartmentinfoapp.domain.restaurants.RestaurantDataDto
import com.example.apartmentinfoapp.domain.shops.ShopDataDto
import com.example.apartmentinfoapp.domain.sights.SightDataDto
import com.example.apartmentinfoapp.presentation.activities.formatImageUrl
import com.example.apartmentinfoapp.presentation.activities.formatImageUrls


inline fun <reified T> List<T>.formatImages(): List<T> {
    return this.map { item ->
        when (item) {
            is RestaurantDataDto -> {
                RestaurantDataDto(
                    restaurantId = item.restaurantId,
                    titleImage = formatImageUrl(item.titleImage),
                    imagesUrl = formatImageUrls(item.imagesUrl),
                    title = item.title,
                    location = item.location,
                    description = item.description,
                    emailContact = item.emailContact,
                    phoneContact = item.phoneContact,
                    review = item.review,
                    reviewAmount = item.reviewAmount
                ) as T
            }

            is SightDataDto -> {
                SightDataDto(
                    sightId = item.sightId,
                    titleImage = formatImageUrl(item.titleImage),
                    imagesUrl = formatImageUrls(item.imagesUrl),
                    title = item.title,
                    location = item.location,
                    description = item.description
                ) as T
            }

            is ShopDataDto -> {
                ShopDataDto(
                    shopId = item.shopId,
                    titleImage = formatImageUrl(item.titleImage),
                    imagesUrl = formatImageUrls(item.imagesUrl),
                    title = item.title,
                    location = item.location,
                    description = item.description
                ) as T
            }

            is DeviceDataDto -> {
                DeviceDataDto(
                    deviceId = item.deviceId,
                    titleImage = formatImageUrl(item.titleImage),
                    imagesUrl = formatImageUrls(item.imagesUrl),
                    title = item.title,
                    description = item.description
                ) as T
            }

            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}

fun AboutUsDataDto.formatAboutUsImages(): AboutUsDataDto {
    return AboutUsDataDto(
        titleImage = formatImageUrl(this.titleImage),
        imagesUrl = formatImageUrls(this.imagesUrl),
        aboutUs = this.aboutUs,
        moto = this.moto
    )
}