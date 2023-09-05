package com.example.wera.domain.repository


import com.example.wera.data.network.GetReceiverIdInterface
import com.example.wera.domain.models.ReceiverIdResponse
import javax.inject.Inject

class GetReceiverIdRepository @Inject constructor(private val getReceiverIdInterface: GetReceiverIdInterface) {
    suspend fun getReceiverId(userId:String, chatId:String) : ReceiverIdResponse{
        return getReceiverIdInterface.getReceiverId(userId, chatId)
    }
}

