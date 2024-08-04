package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.data.repository.ReviewRepositoryImpl
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.ReviewExistanceState
import com.example.apartmentinfoapp.presentation.states.ReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepositoryImpl,
    private val accessTokenProvider: AccessTokenProvider
) :
    ViewModel() {

    var reviewSubmitState by mutableStateOf(ReviewState())
        private set

    private val _state = MutableStateFlow(ReviewExistanceState())
    val state: StateFlow<ReviewExistanceState> get() = _state.asStateFlow()

    fun submitReview(
        review: String,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int
    ) {
        viewModelScope.launch {
            val apartmentId = accessTokenProvider.getApartmentId()
            reviewSubmitState = reviewSubmitState.copy(isLoading = true, error = null)
            when (val result =
                repository.submitReview(
                    review,
                    expRating,
                    comfortRating,
                    valueRating,
                    apartmentId
                )) {
                is Resource.Success -> {
                    reviewSubmitState =
                        reviewSubmitState.copy(isSuccess = true, isLoading = false, error = null)
                }

                is Resource.Error -> {
                    reviewSubmitState =
                        reviewSubmitState.copy(
                            isSuccess = false,
                            isLoading = false,
                            error = result.message
                        )
                }
            }
        }
    }

    fun checkIfReviewExists() {
        viewModelScope.launch {
            val apartmentId = accessTokenProvider.getApartmentId()
            _state.value = _state.value.copy(isLoading = true, error = null)
            when (val result = repository.isReviewAlreadyIsSubmitted(apartmentId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = null,
                        isSuccess = true,
                        isReviewAlreadySubmitted = result.data
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message,
                        isSuccess = false,
                    )
                }
            }
        }
    }


}
