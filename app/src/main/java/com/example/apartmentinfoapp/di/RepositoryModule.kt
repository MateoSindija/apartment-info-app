package com.example.apartmentinfoapp.di

import com.example.apartmentinfoapp.data.repository.AboutUsRepositoryImpl
import com.example.apartmentinfoapp.data.repository.BeachesRepositoryImpl
import com.example.apartmentinfoapp.data.repository.DevicesRepositoryImpl
import com.example.apartmentinfoapp.data.repository.ReservationRepositoryImpl
import com.example.apartmentinfoapp.data.repository.RestaurantRepositoryImpl
import com.example.apartmentinfoapp.data.repository.ShopRepositoryImpl
import com.example.apartmentinfoapp.data.repository.SightRepositoryImpl
import com.example.apartmentinfoapp.data.repository.WeatherRepositoryImpl
import com.example.apartmentinfoapp.domain.repository.AboutUsRepository
import com.example.apartmentinfoapp.domain.repository.BeachesRepository
import com.example.apartmentinfoapp.domain.repository.DevicesRepository
import com.example.apartmentinfoapp.domain.repository.ReservationRepository
import com.example.apartmentinfoapp.domain.repository.RestaurantRepository
import com.example.apartmentinfoapp.domain.repository.ShopRepository
import com.example.apartmentinfoapp.domain.repository.SightRepository
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindBeachesRepository(
        beachesListRepository: BeachesRepositoryImpl
    ): BeachesRepository

    @Binds
    @Singleton
    abstract fun bindDevicesRepository(
        devicesRepository: DevicesRepositoryImpl
    ): DevicesRepository

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        restaurantRepository: RestaurantRepositoryImpl
    ): RestaurantRepository

    @Binds
    @Singleton
    abstract fun bindSightRepository(
        sightRepository: SightRepositoryImpl
    ): SightRepository

    @Binds
    @Singleton
    abstract fun bindShopRepository(
        shopsRepository: ShopRepositoryImpl
    ): ShopRepository

    @Binds
    @Singleton
    abstract fun bindAboutUsRepository(
        aboutUsRepository: AboutUsRepositoryImpl
    ): AboutUsRepository

    @Binds
    @Singleton
    abstract fun bindReservationRepository(
        reservationRepository: ReservationRepositoryImpl
    ): ReservationRepository
}