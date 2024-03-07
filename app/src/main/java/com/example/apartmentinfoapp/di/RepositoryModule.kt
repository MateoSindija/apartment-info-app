package com.example.apartmentinfoapp.di

import com.example.apartmentinfoapp.data.repository.BeachesRepositoryImpl
import com.example.apartmentinfoapp.data.repository.DevicesRepositoryImpl
import com.example.apartmentinfoapp.data.repository.RestaurantRepositoryImpl
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import com.example.apartmentinfoapp.data.repository.WeatherRepositoryImpl
import com.example.apartmentinfoapp.domain.repository.BeachesRepository
import com.example.apartmentinfoapp.domain.repository.DevicesRepository
import com.example.apartmentinfoapp.domain.repository.RestaurantRepository
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
}