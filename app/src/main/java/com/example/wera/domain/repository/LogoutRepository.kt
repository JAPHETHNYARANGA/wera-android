package com.example.wera.domain.repository

import com.example.wera.data.network.Logout
import com.example.wera.domain.models.LogoutResponse
import retrofit2.Response
import javax.inject.Inject


class LogoutRepository @Inject constructor(private val logout: Logout){
    suspend fun logoutUser() : Response<LogoutResponse> {
        return logout.logout()
    }
}


