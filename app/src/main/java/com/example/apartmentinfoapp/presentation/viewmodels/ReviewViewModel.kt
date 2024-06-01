package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.repository.ReviewRepositoryImpl
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.ReviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(private val repository: ReviewRepositoryImpl) :
    ViewModel() {

    var reviewSubmitState by mutableStateOf(ReviewState())
        private set

    fun submitReview(
        review: String,
        expRating: Int,
        comfortRating: Int,
        valueRating: Int
    ) {
        viewModelScope.launch {
            reviewSubmitState = reviewSubmitState.copy(isLoading = false, error = null)
            when (val result =
                repository.submitReview(review, expRating, comfortRating, valueRating)) {
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


}
