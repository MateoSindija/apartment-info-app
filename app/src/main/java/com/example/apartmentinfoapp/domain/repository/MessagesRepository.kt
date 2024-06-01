package com.example.apartmentinfoapp.domain.repository

import com.example.apartmentinfoapp.domain.messages.MessageData
import com.example.apartmentinfoapp.domain.util.Resource

interface MessagesRepository {
    suspend fun getMessages(): Resource<List<MessageData>>
    suspend fun sendMessage(message: String): Resource<Boolean>
}