package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.repository.BeachesRepository
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import com.example.apartmentinfoapp.domain.util.Resource
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
    private val locationTracker: LocationTracker
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
                repository.getBeachesList(lat = location.latitude, lng = location.longitude)
                    .let { beachData ->
                        val lngList = beachData.data?.map { it.lng }?.joinToString(",") ?: ""
                        val latList = beachData.data?.map { it.lat }?.joinToString(",") ?: ""

                        when (val weatherResult =
                            weatherRepository.getMultipleWeatherData(latList, lngList)) {

                            is Resource.Success -> {
                                beachData.data?.forEachIndexed { index, it ->
                                    it.weatherData =
                                        weatherResult.data?.get(index)?.currentWeatherData
                                }
                                _state.value = _state.value.copy(
                                    beachInfo = beachData.data,
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
                                    error = beachData.message
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