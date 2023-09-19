package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.Messages
import com.werrah.wera.domain.repository.GetMessagesRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val getMessagesRepository: GetMessagesRepository) {
    suspend fun getMessagesUseCase(userId: String): Messages{
        return getMessagesRepository.getMessages(userId)
    }
}