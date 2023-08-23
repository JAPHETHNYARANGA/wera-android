package com.example.wera.domain.useCase

import com.example.wera.domain.models.Messages
import com.example.wera.domain.repository.GetMessagesRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(private val getMessagesRepository: GetMessagesRepository) {
    suspend fun getMessagesUseCase(userId: String): Messages{
        return getMessagesRepository.getMessages(userId)
    }
}