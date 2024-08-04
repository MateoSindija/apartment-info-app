package com.example.apartmentinfoapp.presentation.viewmodels

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.domain.repository.ReservationRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.ReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val repository: ReservationRepository,
    private val accessTokenProvider: AccessTokenProvider
) :
    ViewModel() {
    private val _state = MutableStateFlow(ReservationState())
    val state: StateFlow<ReservationState> get() = _state.asStateFlow()

    fun loadReservationInfo(activity: Activity) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            val apartmentId = accessTokenProvider.getApartmentId()
            when (val result = repository.getCurrentReservation(
                apartmentId
            )) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        reservationInfo = result.data,
                        isLoading = false,
                        error = null
                    )

                    if (result.data != null) {
                        val sharedPref =
                            activity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("reservationId", result.data.reservationId).apply()
                        }
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        reservationInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }


            }
        }
    }
}