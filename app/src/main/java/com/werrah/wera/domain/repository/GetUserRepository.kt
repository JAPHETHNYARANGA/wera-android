package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetUser
import com.werrah.wera.domain.models.GetUserData
import javax.inject.Inject

class GetUserRepository @Inject constructor(private val getUser: GetUser) {
    suspend fun getUserProfile() : GetUserData{
        return getUser.getUser()
    }
}


