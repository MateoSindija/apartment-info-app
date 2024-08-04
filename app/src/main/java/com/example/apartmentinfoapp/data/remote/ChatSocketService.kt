package com.example.apartmentinfoapp.data.remote

import com.example.apartmentinfoapp.BuildConfig
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.domain.messages.Message
import com.example.apartmentinfoapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {


    suspend fun initSession(
        reservationId: String,
        userId: String,
        accessTokenProvider: AccessTokenProvider
    ): Resource<Unit>

    suspend fun observeMessages(): Flow<List<Message>>

    suspend fun observeNewMessage(): Flow<Message>

    suspend fun sendMessage(
        userId: String,
        messageBody: String,
        apartmentId: String,
        reservationId: String,
        senderId: String,
    ): Resource<Unit>

    suspend fun closeSession()

    companion object {
        var serverLocation = BuildConfig.SERVER_LOCATION
    }

    sealed class Endpoints(val url: String) {
        object ChatSocket : Endpoints(serverLocation)
    }
}