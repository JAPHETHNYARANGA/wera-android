package com.example.wera.domain.repository

import com.example.wera.data.network.GetChatIdInterface
import com.example.wera.domain.models.ChatId
import javax.inject.Inject


class GetChatIdRepository @Inject constructor(private val getChatIdInterface: GetChatIdInterface){
    suspend fun getChatId(senderId:String, receiverId:String) : ChatId {
        return getChatIdInterface.getChatId(senderId, receiverId)
    }
}

