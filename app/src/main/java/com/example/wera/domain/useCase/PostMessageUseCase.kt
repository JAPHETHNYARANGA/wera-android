package com.example.wera.domain.useCase


import android.util.Log
import com.example.wera.domain.models.PostMessageResponse
import com.example.wera.domain.repository.PostMessageRepository
import java.lang.Exception
import javax.inject.Inject

class PostMessageUseCase @Inject constructor(private  val postMessageRepository: PostMessageRepository) {
    suspend fun postMessage(
        message : String, senderId:String, receiverId:String
    ) : PostMessageResponse{
        try {
            return postMessageRepository.postMessage(message, senderId, receiverId)
        }catch (e: Exception){
            throw e
        }
    }
}

