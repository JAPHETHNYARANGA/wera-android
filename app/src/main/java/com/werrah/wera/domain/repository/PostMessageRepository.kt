package com.werrah.wera.domain.repository



import com.werrah.wera.data.network.PostMessageInterface
import com.werrah.wera.domain.models.PostMessage
import com.werrah.wera.domain.models.PostMessageResponse
import javax.inject.Inject


class PostMessageRepository @Inject constructor(private val postMessage: PostMessageInterface) {
    suspend fun postMessage(message : String, senderId:String, receiverId:String ) : PostMessageResponse{
        val messageRequest = PostMessage(message, senderId, receiverId)
        val response = postMessage.postMessage(messageRequest)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed creating message: ${response.raw()}")
        }
    }
}


