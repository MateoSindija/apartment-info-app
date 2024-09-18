package com.example.apartmentinfoapp.domain.messages

import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import org.json.JSONObject
import java.time.LocalDateTime


data class MessageDataDto(
    val apartmentId: String,
    val isRead: Boolean,
    val messageBody: String,
    val messageId: String,
    val senderId: String,
    val userId: String,
    val createdAt: LocalDateTime
) {
    fun toMessage(accessTokenProvider: AccessTokenProvider): Message {
        val apartmentId = accessTokenProvider.getApartmentId()
        val isMessageMine = senderId == apartmentId

        return Message(
            messageId = messageId,
            text = messageBody,
            isRead = isRead,
            timestamp = createdAt,
            isMessageMine = isMessageMine
        )
    }
}

data class NewMessageObject(
    val userId: String,
    val messageBody: String,
    val apartmentId: String,
    val reservationId: String,
    val senderId: String,
) {
    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("userId", userId)
        json.put("messageBody", messageBody)
        json.put("apartmentId", apartmentId)
        json.put("reservationId", reservationId)
        json.put("senderId", senderId)
        return json
    }
}

data class Message(
    val messageId: String,
    val text: String,
    val isMessageMine: Boolean,
    val timestamp: LocalDateTime,
    val isRead: Boolean,
)


data class JoinRoomObject(
    val reservationId: String,
    val userId: String,
) {
    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("reservationId", reservationId)
        json.put("userId", userId)
        return json
    }
}