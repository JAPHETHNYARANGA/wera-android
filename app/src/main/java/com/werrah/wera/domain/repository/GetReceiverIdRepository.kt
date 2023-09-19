package com.werrah.wera.domain.repository


import com.werrah.wera.data.network.GetReceiverIdInterface
import com.werrah.wera.domain.models.ReceiverIdResponse
import javax.inject.Inject

class GetReceiverIdRepository @Inject constructor(private val getReceiverIdInterface: GetReceiverIdInterface) {
    suspend fun getReceiverId(userId:String, chatId:String) : ReceiverIdResponse{
        return getReceiverIdInterface.getReceiverId(userId, chatId)
    }
}

