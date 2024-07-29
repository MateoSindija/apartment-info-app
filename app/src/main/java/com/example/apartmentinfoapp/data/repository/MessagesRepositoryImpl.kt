package com.example.apartmentinfoapp.data.repository

import android.util.Log
import com.example.apartmentinfoapp.domain.messages.MessageData
import com.example.apartmentinfoapp.domain.repository.MessagesRepository
import com.example.apartmentinfoapp.domain.util.Resource
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor() : MessagesRepository {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getMessages(): Resource<List<MessageData>> {
        return try {
            val snapshot = firestore.collection("Messages").get().await()
            if (snapshot.isEmpty) {
                Resource.Success(emptyList())
            } else {
                val ownerMessagesSnapshot =
                    snapshot.documents.firstOrNull { it.id == "OwnerMessages" }
                val userMessagesSnapshot =
                    snapshot.documents.firstOrNull { it.id == "UserMessages" }

                val ownerMessages =
                    ownerMessagesSnapshot?.let { fetchMessages(it.reference, "owner") }
                        ?: emptyList()
                val userMessages =
                    userMessagesSnapshot?.let { fetchMessages(it.reference, "user") } ?: emptyList()
                Log.d("tu smo ej", userMessages.toString())
                val combinedMessages = (ownerMessages + userMessages).sortedBy { it.timestamp }
                Resource.Success(combinedMessages)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun sendMessage(message: String): Resource<Boolean> {
        return try {
            val newMessage = hashMapOf(
                "message" to message,
                "timestamp" to FieldValue.serverTimestamp()
            )

            firestore.collection("Messages").document("UserMessages")
                .collection("Messages")
                .add(newMessage)
                .await()

            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    private suspend fun fetchMessages(
        documentReference: DocumentReference,
        type: String
    ): List<MessageData> {
        val subcollectionSnapshot = documentReference.collection("Messages").get().await()
        return subcollectionSnapshot.documents.mapNotNull { doc ->
            doc.toObject(MessageData::class.java)?.copy(id = doc.id, userType = type)
                ?: throw Exception("Error converting document to MessageData")
        }
    }
}
