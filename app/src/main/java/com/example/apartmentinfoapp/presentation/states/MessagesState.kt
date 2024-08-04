package com.example.apartmentinfoapp.presentation.states

import com.example.apartmentinfoapp.domain.messages.Message

data class MessagesState(
    val messages: List<Message>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MessageSendState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
