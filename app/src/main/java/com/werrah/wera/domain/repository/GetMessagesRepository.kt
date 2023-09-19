package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetMessages
import com.werrah.wera.domain.models.Messages
import javax.inject.Inject

class GetMessagesRepository @Inject constructor(private val getMessages: GetMessages) {
    suspend fun getMessages(userId: String): Messages{
        return getMessages.getMessages(userId)
    }
}

