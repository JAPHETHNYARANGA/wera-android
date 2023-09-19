package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.Logout
import com.werrah.wera.domain.models.LogoutResponse
import retrofit2.Response
import javax.inject.Inject


class LogoutRepository @Inject constructor(private val logout: Logout){
    suspend fun logoutUser() : Response<LogoutResponse> {
        return logout.logout()
    }
}


