package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.domain.repository.DevicesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.DevicesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(private val repository: DevicesRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(DevicesListState())
    val state: StateFlow<DevicesListState> get() = _state.asStateFlow()


    fun loadDevicesList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            when (val result =
                repository.getDevicesList()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        devicesInfoList = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        devicesInfoList = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }

    }
}