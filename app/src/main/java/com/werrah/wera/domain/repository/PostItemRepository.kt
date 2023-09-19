package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.PostItem
import com.werrah.wera.domain.models.PostItemData
import com.werrah.wera.domain.models.PostItemResponse
import javax.inject.Inject

class PostItemRepository  @Inject constructor(private val postItem: PostItem){
    suspend fun postItem(name :String, description : String, location: String, amount : String, category : Int, status: Int, image:String) : PostItemResponse{
        val postItemRequest = PostItemData(name, description, location, amount, category, status, image)
        val response = postItem.post(postItemRequest)

        if (response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed data post ${response.raw()}")
        }
    }
}





