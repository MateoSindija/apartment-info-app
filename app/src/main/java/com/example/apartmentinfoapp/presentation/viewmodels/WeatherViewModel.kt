package com.example.apartmentinfoapp.presentation.viewmodels

import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.MultipleWeatherState
import com.example.apartmentinfoapp.presentation.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    var multipleWeather by mutableStateOf(MultipleWeatherState())
        private set


    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {

                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    fun loadMultipleWeatherInfo(latList: String, lngList: String) {
        viewModelScope.launch {
            multipleWeather = multipleWeather.copy(
                isLoading = true,
                error = null
            )
            when (val result =
                repository.getMultipleWeatherData(latList, lngList)) {
                is Resource.Success -> {
                    multipleWeather = multipleWeather.copy(
                        weatherInfoList = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    multipleWeather = multipleWeather.copy(
                        weatherInfoList = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }


        }
    }
}