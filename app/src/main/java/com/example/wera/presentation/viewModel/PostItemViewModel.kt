package com.example.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.models.PostItemResponse
import com.example.wera.domain.useCase.LoginUserUseCase
import com.example.wera.domain.useCase.PostItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.security.auth.login.LoginException


@HiltViewModel
class PostItemViewModel @Inject constructor(private val postItemUseCase: PostItemUseCase) : ViewModel() {

    private val _postItem = MutableLiveData<PostItemResponse>()

    val postItem : LiveData<PostItemResponse> get() = _postItem

    private val _postItemSuccess = MutableLiveData<Boolean>()
    val postItemSuccess : LiveData<Boolean> get() = _postItemSuccess

    suspend fun postItem(
        name: String,
        description : String,
        location : String,
        amount : String,
        category : String

    ) : PostItemResponse{
        try {
            return postItemUseCase.postItemUseCase(name, description, location, amount, category)
        }catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw LoginException("Request timed out")
        }
    }
}






