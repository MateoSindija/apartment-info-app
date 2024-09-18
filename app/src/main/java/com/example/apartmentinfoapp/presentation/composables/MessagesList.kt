package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apartmentinfoapp.presentation.states.MessagesState

@Composable
fun MessagesList(messagesState: MessagesState, modifier: Modifier) {
    val listOfMessagesData = messagesState.messages.orEmpty()


    if (listOfMessagesData.isNotEmpty()) {
        LazyColumn(modifier = modifier) {
            items(listOfMessagesData, key = { message -> message.messageId }) { messageData ->
                // Display each message with a MessageRow
                MessageRow(messageData)
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}