package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.repository.RestaurantRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.RestaurantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    private val locationTracker: LocationTracker,
    private val accessTokenProvider: AccessTokenProvider
) :
    ViewModel() {
    private val _state = MutableStateFlow(RestaurantState())
    val state: StateFlow<RestaurantState> get() = _state.asStateFlow()

    fun loadRestaurantsInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val apartmentId = accessTokenProvider.getApartmentId()
                when (val result = repository.getRestaurantsList(
                    apartmentId
                )) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            restaurantsInfoList = result.data,
                            mineLat = location.latitude,
                            mineLng = location.longitude,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            restaurantsInfoList = null,
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
}