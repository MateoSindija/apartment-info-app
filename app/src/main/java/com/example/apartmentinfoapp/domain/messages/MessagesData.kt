package com.example.apartmentinfoapp.domain.messages

import java.util.Date

data class MessagesCollection(
    val OwnerMessages: MessagesSubCollection? = null,
    val UserMessages: MessagesSubCollection? = null
)

data class MessagesSubCollection(
    val Messages: List<MessageData>? = null
)

data class MessageData(
    val id: String? = null,
    val userType: String? = null,
    val message: String? = null,
    val timestamp: Date? = null
)
