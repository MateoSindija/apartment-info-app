package com.example.apartmentinfoapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apartmentinfoapp.data.repository.MessagesRepositoryImpl
import com.example.apartmentinfoapp.domain.util.Resource
import com.example.apartmentinfoapp.presentation.states.MessageSendState
import com.example.apartmentinfoapp.presentation.states.MessagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(private val repository: MessagesRepositoryImpl) :
    ViewModel() {
    private val _getState = MutableStateFlow(MessagesState())
    val getState: StateFlow<MessagesState> get() = _getState.asStateFlow()

    var messagesSendState by mutableStateOf(MessageSendState())
        private set


    fun loadMessages() {
        viewModelScope.launch {
            _getState.value = _getState.value.copy(
                isLoading = true,
                error = null
            )
            when (val result = repository.getMessages()) {
                is Resource.Success -> {
                    _getState.value = _getState.value.copy(
                        messages = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _getState.value = _getState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            messagesSendState = messagesSendState.copy(isLoading = true, error = null)
            when (val result = repository.sendMessage(message)) {
                is Resource.Success -> {
                    messagesSendState = messagesSendState.copy(
                        isLoading = false,
                        error = null,
                        isSuccess = true
                    )
                    loadMessages()
                }

                is Resource.Error -> {
                    messagesSendState = messagesSendState.copy(
                        isLoading = false,
                        error = result.message,
                        isSuccess = false
                    )
                }
            }
        }
    }

}