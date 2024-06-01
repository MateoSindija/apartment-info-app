package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.messages.MessageData

data class MessagesState(
    val messages: List<MessageData>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MessageSendState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
