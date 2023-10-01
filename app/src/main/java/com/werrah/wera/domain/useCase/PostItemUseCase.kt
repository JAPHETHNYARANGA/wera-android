package com.werrah.wera.domain.useCase

import android.util.Log
import com.werrah.wera.domain.models.PostItemResponse
import com.werrah.wera.domain.repository.PostItemRepository
import java.lang.Exception
import javax.inject.Inject

class PostItemUseCase @Inject constructor(private val postItemRepository: PostItemRepository) {
    suspend fun postItemUseCase(
        name : String,
        description : String,
        location : String,
        sublocation : String,
        amount : String,
        category : Int,
        status : Int,
        image: String
    ) : PostItemResponse{
        try {
            return postItemRepository.postItem(name, description, location,sublocation, amount, category, status, image)
        }catch (e: Exception){
            Log.d("post item useCase Error", "${e.message}")
            throw e
        }
    }
}



