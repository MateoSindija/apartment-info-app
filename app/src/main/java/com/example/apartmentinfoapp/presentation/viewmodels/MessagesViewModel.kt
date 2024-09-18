package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.data.remote.ChatSocketService
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.MessagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val chatSocketService: ChatSocketService,
    private val accessTokenProvider: AccessTokenProvider,
) : ViewModel() {


    private val _state = MutableStateFlow(MessagesState())
    val state: StateFlow<MessagesState> = _state.asStateFlow()


    fun startChatSession() {
        connectToChat()
    }

    private fun connectToChat() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val reservationId = accessTokenProvider.getReservationId()
            val userId = accessTokenProvider.getOwnerId()

            val result = chatSocketService.initSession(reservationId, userId, accessTokenProvider)
            when (result) {
                is Resource.Success -> {
                    observeMessages()
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    private suspend fun observeMessages() {
        chatSocketService.observeMessages()
            .onEach { messagesList ->
                _state.value = _state.value.copy(
                    messages = messagesList,
                    isLoading = false
                )
            }.launchIn(viewModelScope)

        chatSocketService.observeNewMessage()
            .onEach { message ->
                val currentMessages = _state.value.messages?.toMutableList() ?: mutableListOf()
                currentMessages.add(message)
                _state.value = _state.value.copy(
                    messages = currentMessages,
                    isLoading = false
                )
            }.launchIn(viewModelScope)
    }


    fun sendMessage(message: String) {
        viewModelScope.launch {
            if (message.isNotBlank()) {
                val ownerId = accessTokenProvider.getOwnerId()
                val apartmentId = accessTokenProvider.getApartmentId()
                val reservationId = accessTokenProvider.getReservationId()
                chatSocketService.sendMessage(
                    userId = ownerId,
                    messageBody = message,
                    apartmentId = apartmentId,
                    reservationId = reservationId,
                    senderId = apartmentId
                )
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }
}