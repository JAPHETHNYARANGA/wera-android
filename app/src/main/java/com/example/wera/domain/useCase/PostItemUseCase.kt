package com.example.wera.domain.useCase

import android.util.Log
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.models.PostItemResponse
import com.example.wera.domain.repository.LoginUserRepository
import com.example.wera.domain.repository.PostItemRepository
import java.lang.Exception
import javax.inject.Inject

class PostItemUseCase @Inject constructor(private val postItemRepository: PostItemRepository) {
    suspend fun postItemUseCase(
        name : String,
        description : String,
        location : String,
        amount : String,
        category : String
    ) : PostItemResponse{
        try {
            return postItemRepository.postItem(name, description, location, amount, category)
        }catch (e: Exception){
            Log.d("post item useCase Error", "${e.message}")
            throw e
        }
    }
}



