package com.example.apartmentinfoapp.presentation.activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.apartmentinfoapp.R
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.domain.messages.Message
import com.example.apartmentinfoapp.presentation.composables.MessagesList
import com.example.apartmentinfoapp.presentation.states.MessagesState
import com.example.apartmentinfoapp.presentation.viewmodels.MessagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.LocalDateTime


@AndroidEntryPoint
class MessageActivity : ComponentActivity() {
    private val viewModelMessages: MessagesViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.apartmentinfoapp.presentation.ui.theme.ApartmentInfoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF2F4F5)
                ) {
                    MessageScreen(
                        viewModelMessages = viewModelMessages,
                        onFinishActivity = { finish() },
                        context = this
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getLastMessageActivity(messages: List<Message>): String {
    val lastMessageTimestamp = messages.lastOrNull()?.timestamp ?: return "No messages yet"
    val currentDateTime = LocalDateTime.now()
    val duration = Duration.between(lastMessageTimestamp, currentDateTime)

    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60

    return when {
        days >= 30 -> {
            val months = days / 30
            "$months month${if (months > 1) "s" else ""} ago"
        }

        days >= 1 -> {
            "$days day${if (days > 1) "s" else ""} ago"
        }

        hours >= 1 -> {
            "$hours hour${if (hours > 1) "s" else ""} ago"
        }

        else -> {
            "$minutes minute${if (minutes > 1) "s" else ""} ago"
        }
    }
}

fun sendMessage(viewModelMessages: MessagesViewModel, messageState: String) {
    if (messageState.isNotEmpty()) {
        viewModelMessages.sendMessage(messageState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageScreen(
    viewModelMessages: MessagesViewModel = hiltViewModel(),
    onFinishActivity: () -> Unit,
    context: Context
) {

    var textFiledState by remember { mutableStateOf("") }
    val lifecycleOwner = LocalLifecycleOwner.current
    val reservationId = AccessTokenProvider(context).getReservationId()


    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START && reservationId.isNotEmpty()) {
                viewModelMessages.startChatSession()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModelMessages.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by viewModelMessages.state.collectAsState(MessagesState())



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 40.dp,
                bottom = 15.dp,
                top = 15.dp,
                end = 40.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .padding(bottom = 10.dp)
        ) {
            Button(
                onClick = onFinishActivity,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier.width(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "arrow",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
            Image(
                painter = painterResource(id = R.drawable.landlord_image),
                contentDescription = "Landlord avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = "Apartment Host",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.space_cadet)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = state.messages?.let { getLastMessageActivity(it) } ?: "",
                    fontSize = 12.sp
                )
            }
        }
        if (reservationId.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(570.dp)
            ) {
                MessagesList(
                    messagesState = state,
                    modifier = Modifier.height(580.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(top = 5.dp)
            ) {
                MessageTextField(
                    Modifier
                        .weight(0.7f)
                        .fillMaxHeight()
                        .background(color = Color.White),
                    textState = textFiledState,
                    onTextChanged = { textFiledState = it },
                )
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    onClick = {
                        sendMessage(
                            viewModelMessages = viewModelMessages,
                            messageState = textFiledState
                        )
                        textFiledState = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.tufts_blue)
                    ),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "Send",
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.white)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Send message",
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "There are no current reservation active")
            }
        }
    }
}

@Composable
fun MessageTextField(
    modifier: Modifier,
    textState: String,
    onTextChanged: (String) -> Unit,
) {

    TextField(
        value = textState,
        onValueChange = onTextChanged,
        placeholder = { Text("Type here...") },
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent, // Hide the underline indicator
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = colorResource(id = R.color.gains_boro)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}