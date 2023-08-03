package com.example.wera.domain.repository

import com.example.wera.data.network.GetUser
import com.example.wera.domain.models.GetUserData
import javax.inject.Inject

class GetUserRepository @Inject constructor(private val getUser: GetUser) {
    suspend fun getUserProfile() : GetUserData{
        return getUser.getUser()
    }
}


