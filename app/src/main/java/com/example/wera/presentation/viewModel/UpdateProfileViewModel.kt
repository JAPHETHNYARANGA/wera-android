package com.example.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wera.domain.models.UpdateProfileResponse
import com.example.wera.domain.useCase.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject



@HiltViewModel
class UpdateProfileViewModel @Inject constructor(private val updateProfileUseCase: UpdateProfileUseCase) : ViewModel(){
    private val _updateItem = MutableLiveData<UpdateProfileResponse>()

    val updateProfile : LiveData<UpdateProfileResponse> get() = _updateItem

    private val _updateProfileSuccess = MutableLiveData<Boolean>()
    val updateProfileSuccess : LiveData<Boolean> get() = _updateProfileSuccess

    suspend fun updateProfile(
        name: String, email: String, phone: String, bio: String, occupation:String
    ):UpdateProfileResponse{
        try {
            return updateProfileUseCase.updateProfile(name, email, phone, bio, occupation)
        }catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw LoginException("Request timed out")
        }
    }
}

