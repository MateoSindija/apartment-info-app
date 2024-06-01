package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.repository.WeatherRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.MultipleWeatherState
import com.example.apartmentinfoapp.presentation.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> get() = _state.asStateFlow()


    private val _multipleWeatherState = MutableStateFlow(MultipleWeatherState())
    val multipleWeatherState: StateFlow<MultipleWeatherState> get() = _multipleWeatherState.asStateFlow()


    fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
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

    fun loadMultipleWeatherInfo(latList: String, lngList: String) {
        viewModelScope.launch {
            _multipleWeatherState.value = _multipleWeatherState.value.copy(
                isLoading = true,
                error = null
            )
            when (val result =
                repository.getMultipleWeatherData(latList, lngList)) {
                is Resource.Success -> {
                    _multipleWeatherState.value = _multipleWeatherState.value.copy(
                        weatherInfoList = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _multipleWeatherState.value = _multipleWeatherState.value.copy(
                        weatherInfoList = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }


        }
    }
}