package com.example.apartmentinfoapp.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.domain.messages.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MessageRow(messageData: Message) {
    when (messageData.isMessageMine) {
        true -> Message(
            messageData = messageData,
            background = Color.Blue,
            position = Arrangement.End,
            textColor = Color.White
        )

        false -> Message(
            messageData = messageData,
            background = colorResource(id = R.color.gains_boro),
            position = Arrangement.Start,
            textColor = Color.Black
        )

    }
}

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}


@Composable
fun Message(
    messageData: Message,
    background: Color,
    position: Arrangement.Horizontal,
    textColor: Color
) {
    var isTimeVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = position
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .widthIn(max = 600.dp)
                    .background(
                        color = background,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .clickable { isTimeVisible = !isTimeVisible }
            ) {
                messageData.text.let {
                    Text(
                        text = it,
                        color = textColor,
                    )
                }
            }
            if (isTimeVisible) {
                Text(text = messageData.timestamp.toString(), fontSize = 12.sp)
            }
        }
    }
}