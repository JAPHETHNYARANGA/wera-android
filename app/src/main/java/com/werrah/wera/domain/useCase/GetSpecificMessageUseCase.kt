package com.werrah.wera.domain.useCase


import com.werrah.wera.domain.models.Messages
import com.werrah.wera.domain.repository.GetSpecifiMessageRepository
import javax.inject.Inject


class GetSpecificMessageUseCase @Inject constructor(private val getSpecifiMessageRepository: GetSpecifiMessageRepository) {
    suspend fun getSpecificMessageUseCase(chatId: String) : Messages {
        return getSpecifiMessageRepository.getSpecificMessage(chatId)
    }
}


