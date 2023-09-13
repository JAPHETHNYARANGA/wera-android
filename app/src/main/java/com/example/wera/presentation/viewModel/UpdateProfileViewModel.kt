package com.example.wera.presentation.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.models.OtherProfile
import com.example.wera.domain.models.UpdateProfileResponse
import com.example.wera.domain.models.UserProfile
import com.example.wera.domain.models.otherUser
import com.example.wera.domain.repository.GetUserRepository
import com.example.wera.domain.useCase.FetchProfileUseCase
import com.example.wera.domain.useCase.UpdateProfileUseCase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.SocketTimeoutException
import javax.inject.Inject



@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getUserRepository: GetUserRepository,
    private val sharedPreferences: SharedPreferences,
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel(){

    private val _updateItem = MutableLiveData<UpdateProfileResponse>()
    private val _isRefreshing = MutableStateFlow(false)
    private val _user = MutableStateFlow<UserProfile?>(null)
    private val _profile = MutableStateFlow<OtherProfile?>(null)
    val profileDisplay: StateFlow<OtherProfile?> = _profile

    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    val updateProfile : LiveData<UpdateProfileResponse> get() = _updateItem
    private val _updateProfileSuccess = MutableLiveData<Boolean>()
    val updateProfileSuccess : LiveData<Boolean> get() = _updateProfileSuccess
    val userId = sharedPreferences.getString("userIdPreference", "") ?: ""

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.value = true
                val profileData: GetUserData = getUserRepository.getUserProfile()
                val user: UserProfile? = profileData.user
                _user.value = user

            } catch (e: Exception) {
                Log.d("Failure fetching Profile data", "${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    suspend fun updateProfile(
        name: String, email: String, phone: String, bio: String, occupation:String, profile: String
    ):UpdateProfileResponse{
        try {
            return updateProfileUseCase.updateProfile(name, email, phone, bio, occupation, profile)
        }catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw LoginException("Request timed out")
        }
    }


    fun fetchOtherProfile(userId: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.value = true
                val profileData: OtherProfile = fetchProfileUseCase.fetchProfileUseCase(userId)

                _profile.value = profileData

                profileData?.user?.profile?.let {storageLocation ->
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.getReference(storageLocation)
                    val imageUrl = storageRef.downloadUrl.await().toString()
                    _imageUrl.value = imageUrl
                }


            } catch (e: Exception) {
                Log.d("Failure fetching Profile data", "${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

}

