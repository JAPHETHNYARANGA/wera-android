package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.ChatId
import com.werrah.wera.domain.repository.GetChatIdRepository
import javax.inject.Inject


class GetChatIdUseCase @Inject constructor(private val getChatIdRepository: GetChatIdRepository) {
    suspend fun getChatId(senderId:String, receiverId:String) : ChatId{
        return getChatIdRepository.getChatId(senderId, receiverId)
    }
}

