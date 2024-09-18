package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.data.remote.LoginDto
import com.example.apartmentinfoapp.domain.util.Resource

interface LoginRepository {
    suspend fun login(apartmentId: String, password: String): Resource<LoginDto>
}