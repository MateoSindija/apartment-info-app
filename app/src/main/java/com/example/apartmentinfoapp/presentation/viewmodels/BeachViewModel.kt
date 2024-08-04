package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.domain.beaches.BeachData
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.repository.BeachesRepository
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.activities.formatImageUrl
import com.example.apartmentinfoapp.presentation.activities.formatImageUrls
import com.example.apartmentinfoapp.presentation.states.BeachState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BeachViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val repository: BeachesRepository,
    private val locationTracker: LocationTracker,
    private val accessTokenProvider: AccessTokenProvider
) : ViewModel() {
    private val _state = MutableStateFlow(BeachState())
    val state: StateFlow<BeachState> get() = _state.asStateFlow()
    fun loadBeachesInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val apartmentId = accessTokenProvider.getApartmentId()
                repository.getBeachesList(
                    apartmentId
                )
                    .let { beachDataDto ->
                        val lngList =
                            beachDataDto.data?.map { it.location.coordinates[1] }
                                ?.joinToString(",") ?: ""
                        val latList =
                            beachDataDto.data?.map { it.location.coordinates[0] }
                                ?.joinToString(",") ?: ""

                        when (val weatherResult =
                            weatherRepository.getMultipleWeatherData(latList, lngList)) {
                            is Resource.Success -> {
                                val beachData: MutableList<BeachData> = mutableListOf()
                                beachDataDto.data?.forEachIndexed { index, it ->
                                    beachData.add(
                                        BeachData(
                                            beachId = it.beachId,
                                            description = it.description,
                                            imagesUrl = formatImageUrls(it.imagesUrl),
                                            location = it.location,
                                            terrainType = it.terrainType,
                                            title = it.title,
                                            titleImage = formatImageUrl(it.titleImage),
                                            weatherData = weatherResult.data?.get(index)?.currentWeatherData
                                        )
                                    )

                                }
                                _state.value = _state.value.copy(
                                    beachInfo = beachData,
                                    mineLat = location.latitude,
                                    mineLng = location.longitude,
                                    isLoading = false,
                                    error = null
                                )
                            }

                            is Resource.Error -> {
                                _state.value = _state.value.copy(
                                    beachInfo = null,
                                    isLoading = false,
                                    error = beachDataDto.message
                                )
                            }
                        }


                    }
            } ?: kotlin.run {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}