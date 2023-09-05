package com.example.wera.domain.repository

import com.example.wera.data.network.GetSpecificMessage
import com.example.wera.domain.models.Messages
import javax.inject.Inject


class GetSpecifiMessageRepository @Inject constructor(private val getSpecificMessage: GetSpecificMessage) {
    suspend fun getSpecificMessage(chatId:String) : Messages{
        return getSpecificMessage.getMessage(chatId)
    }
}


