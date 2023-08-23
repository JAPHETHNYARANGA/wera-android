package com.example.wera.domain.repository

import com.example.wera.data.network.GetMessages
import com.example.wera.domain.models.Messages
import javax.inject.Inject

class GetMessagesRepository @Inject constructor(private val getMessages: GetMessages) {
    suspend fun getMessages(userId: String): Messages{
        return getMessages.getMessages(userId)
    }
}

