package com.example.wera.domain.repository

import com.example.wera.data.network.LoginUser
import com.example.wera.data.network.PostItem
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.models.PostItemData
import com.example.wera.domain.models.PostItemResponse
import com.example.wera.domain.models.UserLogin
import javax.inject.Inject

class PostItemRepository  @Inject constructor(private val postItem: PostItem){
    suspend fun postItem(name :String, description : String, location: String, amount : String, category : String) : PostItemResponse{
        val postItemRequest = PostItemData(name, description, location, amount, category)
        val response = postItem.post(postItemRequest)

        if (response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed data fetching ${response.raw()}")
        }
    }
}





