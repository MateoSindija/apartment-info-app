package com.example.apartmentinfoapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.location.LocationTracker
import com.example.apartmentinfoapp.domain.repository.BeachesListRepository
import com.example.apartmentinfoapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BeachViewModel @Inject constructor(
    private val repository: BeachesListRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    var state by mutableStateOf(BeachState())
        private set

    fun loadBeachesInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getBeachesList(lat = location.latitude, lng = location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            beachInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            beachInfo = null,
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
}