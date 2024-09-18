import android.os.Build
import androidx.annotation.RequiresApi
import com.example.apartmentinfoapp.data.interceptor.AccessTokenProvider
import com.example.apartmentinfoapp.data.remote.ChatSocketService
import com.example.apartmentinfoapp.domain.messages.JoinRoomObject
import com.example.apartmentinfoapp.domain.messages.Message
import com.example.apartmentinfoapp.domain.messages.MessageDataDto
import com.example.apartmentinfoapp.domain.messages.NewMessageObject
import com.example.apartmentinfoapp.domain.util.Resource
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@RequiresApi(Build.VERSION_CODES.O)
class ChatSocketServiceImpl : ChatSocketService {

    private val socket: Socket = IO.socket(ChatSocketService.Endpoints.ChatSocket.url)
    private val messagesChannel = Channel<List<Message>>(Channel.BUFFERED)
    private val newMessageChannel = Channel<Message>(Channel.BUFFERED)
    private val FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun fromJson(json: String): LocalDateTime {
        return try {
            val offsetDateTime = OffsetDateTime.parse(json, FORMATTER)
            offsetDateTime.toLocalDateTime()
        } catch (e: DateTimeParseException) {
            throw RuntimeException("Failed to parse date: $json", e)
        }
    }

    private suspend fun connectSocket(): Boolean {
        val connectionDeferred = CompletableDeferred<Boolean>()

        socket.on(Socket.EVENT_CONNECT) {
            connectionDeferred.complete(true)
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) {
            connectionDeferred.complete(false)
        }

        socket.connect()

        return connectionDeferred.await()
    }

    override suspend fun initSession(
        reservationId: String,
        userId: String,
        accessTokenProvider: AccessTokenProvider
    ): Resource<Unit> {
        return try {
            if (!socket.connected()) {
                val isConnected = connectSocket()
                if (!isConnected) {
                    return Resource.Error("Couldn't establish a connection.")
                }
            }

            val joinRoomObject = JoinRoomObject(reservationId, userId).toJson()
            socket.emit("joinRoom", joinRoomObject)

            socket.on("messages") { args ->
                if (args.isNotEmpty() && args[0] is JSONArray) {
                    val messagesArray = args[0] as JSONArray
                    val newMessagesList = mutableListOf<Message>()

                    for (i in 0 until messagesArray.length()) {
                        val messageObject = messagesArray.getJSONObject(i)
                        val messageData = MessageDataDto(
                            userId = messageObject.getString("userId"),
                            messageBody = messageObject.getString("messageBody"),
                            apartmentId = messageObject.getString("apartmentId"),
                            senderId = messageObject.getString("senderId"),
                            messageId = messageObject.getString("messageId"),
                            isRead = messageObject.getBoolean("isRead"),
                            createdAt = fromJson(messageObject.getString("createdAt")),
                        ).toMessage(accessTokenProvider)
                        newMessagesList.add(messageData)
                    }

                    messagesChannel.trySend(newMessagesList.toList())
                } else {
                    throw Error("Failed to receive messages")
                }
            }

            socket.on("new-message") { args ->
                if (args.isNotEmpty() && args[0] is JSONObject) {
                    val messageObject = args[0] as JSONObject
                    val messageData = MessageDataDto(
                        userId = messageObject.getString("userId"),
                        messageBody = messageObject.getString("messageBody"),
                        apartmentId = messageObject.getString("apartmentId"),
                        senderId = messageObject.getString("senderId"),
                        messageId = messageObject.getString("messageId"),
                        isRead = messageObject.getBoolean("isRead"),
                        createdAt = fromJson(messageObject.getString("createdAt")),
                    ).toMessage(accessTokenProvider)

                    newMessageChannel.trySend(messageData)
                } else {
                    throw Error("Failed to receive new message")
                }
            }

            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(
        userId: String,
        messageBody: String,
        apartmentId: String,
        reservationId: String,
        senderId: String,
    ): Resource<Unit> {
        return try {
            val newMessage =
                NewMessageObject(userId, messageBody, apartmentId, reservationId, senderId).toJson()
            socket.emit("message", newMessage)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to emit message")
        }
    }

    override suspend fun observeMessages(): Flow<List<Message>> {
        return messagesChannel.receiveAsFlow()
    }

    override suspend fun observeNewMessage(): Flow<Message> {
        return newMessageChannel.receiveAsFlow()
    }

    override suspend fun closeSession() {
        socket.disconnect().close()
    }
}
