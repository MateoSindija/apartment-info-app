package com.example.apartmentinfoapp.data.repository

import com.example.apartmentinfoapp.data.remote.LoginApi
import com.example.apartmentinfoapp.data.remote.LoginDto
import com.example.apartmentinfoapp.domain.login.LoginData
import com.example.apartmentinfoapp.domain.repository.LoginRepository
import com.example.apartmentinfoapp.domain.util.Resource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val api: LoginApi) : LoginRepository {
    override suspend fun login(apartmentId: String, password: String): Resource<LoginDto> {
        return try {
            val loginData = LoginData(apartmentId, password)
            Resource.Success(data = api.login(loginData))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}