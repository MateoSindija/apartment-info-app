package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.repository.SightRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.SightsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SightViewModel @Inject constructor(
    private val repository: SightRepository,
    private val locationTracker: LocationTracker
) :
    ViewModel() {
    private val _state = MutableStateFlow(SightsState())
    val state: StateFlow<SightsState> get() = _state.asStateFlow()


    fun loadSightsInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result = repository.getSights(
                    lat = location.latitude,
                    lng = location.longitude
                )) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            sightsInfoList = result.data,
                            mineLat = location.latitude,
                            mineLng = location.longitude,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            sightsInfoList = null,
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