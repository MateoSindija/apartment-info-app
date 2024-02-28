package com.example.apartmentinfoapp.di

import com.example.apartmentinfoapp.data.repository.BeachesListRepositoryImpl
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import com.example.apartmentinfoapp.data.repository.WeatherRepositoryImpl
import com.example.apartmentinfoapp.domain.repository.BeachesListRepository
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
    abstract fun bindBeachesListRepository(
        beachesListRepository: BeachesListRepositoryImpl
    ): BeachesListRepository
}