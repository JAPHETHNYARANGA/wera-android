package com.example.wera.domain.useCase

import com.example.wera.domain.models.ReceiverIdResponse
import com.example.wera.domain.repository.GetReceiverIdRepository
import javax.inject.Inject


class GetReceiverIdUseCase @Inject constructor(private  val getReceiverIdRepository: GetReceiverIdRepository) {
    suspend fun getReceiverId(userId:String, chatId: String) : ReceiverIdResponse{
        return getReceiverIdRepository.getReceiverId(userId, chatId)
    }
}

