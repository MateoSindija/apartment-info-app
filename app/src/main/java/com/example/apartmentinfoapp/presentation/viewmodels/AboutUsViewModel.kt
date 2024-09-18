package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.domain.repository.AboutUsRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.AboutUsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(
    private val aboutUsRepository: AboutUsRepository,
    private val accessTokenProvider: AccessTokenProvider
) : ViewModel() {
    private val _state = MutableStateFlow(AboutUsState())
    val state: StateFlow<AboutUsState> get() = _state.asStateFlow()

    fun loadAboutUsInfo() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            val apartmentId = accessTokenProvider.getApartmentId()
            when (val aboutUsResults = aboutUsRepository.getAboutUs(apartmentId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        aboutUsInfo = aboutUsResults.data,
                        isLoading = false,
                        error = aboutUsResults.message
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        aboutUsInfo = null,
                        isLoading = false,
                        error = aboutUsResults.message
                    )
                }
            }
        }
    }
}