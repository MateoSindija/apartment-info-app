package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.data.remote.LoginDto

data class LoginState(
    val loginResponse: LoginDto? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)

data class LoginSendState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
