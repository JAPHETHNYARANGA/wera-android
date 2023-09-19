package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetChatIdInterface
import com.werrah.wera.domain.models.ChatId
import javax.inject.Inject


class GetChatIdRepository @Inject constructor(private val getChatIdInterface: GetChatIdInterface){
    suspend fun getChatId(senderId:String, receiverId:String) : ChatId {
        return getChatIdInterface.getChatId(senderId, receiverId)
    }
}

