package com.example.wera.presentation.viewModel

import android.content.SharedPreferences
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
class PostItemViewModel @Inject constructor(private val postItemUseCase: PostItemUseCase, private val sharedPreferences: SharedPreferences,) : ViewModel() {

    private val _postItem = MutableLiveData<PostItemResponse>()

    val postItem : LiveData<PostItemResponse> get() = _postItem

    private val _postItemSuccess = MutableLiveData<Boolean>()
    val postItemSuccess : LiveData<Boolean> get() = _postItemSuccess

    val userId = sharedPreferences.getString("userIdPreference", "") ?: ""

    suspend fun postItem(
        name: String,
        description : String,
        location : String,
        amount : String,
        category : Int,
        status : Int

    ) : PostItemResponse{
        try {
            return postItemUseCase.postItemUseCase(name, description, location, amount, category, status)

        }catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw LoginException("Request timed out")
        }
    }
}






