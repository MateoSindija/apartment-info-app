package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.repository.DevicesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.DevicesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(private val repository: DevicesRepository) :
    ViewModel() {
    var devicesListState by mutableStateOf(DevicesListState())
        private set

    fun loadDevicesList() {
        viewModelScope.launch {
            devicesListState = devicesListState.copy(
                isLoading = true,
                error = null
            )
            when (val result =
                repository.getDevicesList()) {
                is Resource.Success -> {
                    devicesListState = devicesListState.copy(
                        devicesInfoList = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    devicesListState = devicesListState.copy(
                        devicesInfoList = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }


        }

    }
}