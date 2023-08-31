package com.example.wera.presentation.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.models.UserData
import com.example.wera.domain.models.UserProfile
import com.example.wera.domain.repository.GetUserRepository
import com.example.wera.domain.useCase.GetUserUseCase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class GetUserViewModel @Inject constructor(private val getUserRepository: GetUserRepository,  private val sharedPreferences: SharedPreferences) : ViewModel() {
    private val _user = MutableStateFlow<UserProfile?>(null)
    val userDisplay: StateFlow<UserProfile?> = _user
    private val _isRefreshing = MutableStateFlow(false)
//    private lateinit var sharedPreferences: SharedPreferences

    val isRefreshing: StateFlow<Boolean> = _isRefreshing

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

                user?.profile?.let { storageLocation ->
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

