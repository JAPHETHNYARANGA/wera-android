package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetSpecificMessage
import com.werrah.wera.domain.models.Messages
import javax.inject.Inject


class GetSpecifiMessageRepository @Inject constructor(private val getSpecificMessage: GetSpecificMessage) {
    suspend fun getSpecificMessage(chatId:String) : Messages{
        return getSpecificMessage.getMessage(chatId)
    }
}


