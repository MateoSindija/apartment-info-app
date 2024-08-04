package com.example.apartmentinfoapp.presentation.viewmodels

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.repository.LoginRepositoryImpl
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepositoryImpl) :
    ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    fun login(apartmentId: String, password: String, activity: Activity) {
        viewModelScope.launch {
            loginState = loginState.copy(isLoading = true, error = null)
            when (val result = repository.login(apartmentId, password)) {
                is Resource.Success -> {
                    loginState = loginState.copy(
                        isLoading = false,
                        error = null,
                        isSuccess = true,
                        loginResponse = result.data
                    )
                    if (result.data != null) {
                        val sharedPref =
                            activity.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("x-access-token", result.data.accessToken).apply()
                            putString("apartmentId", result.data.apartmentId).apply()
                            putString("ownerId", result.data.ownerId).apply()
                        }
                    }
                }

                is Resource.Error -> {
                    loginState = loginState.copy(
                        isLoading = false,
                        error = result.message,
                        isSuccess = false
                    )
                }
            }
        }
    }

}