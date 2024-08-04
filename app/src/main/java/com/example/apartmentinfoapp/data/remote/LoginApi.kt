package com.example.apartmentinfoapp.data.remote

import com.example.apartmentinfoapp.domain.login.LoginData
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login/apartment")
    suspend fun login(
        @Body body: LoginData,
    ): LoginDto
}