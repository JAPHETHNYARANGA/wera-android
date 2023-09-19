package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.FetchProfile
import com.werrah.wera.domain.models.OtherProfile
import javax.inject.Inject

class FetchProfileRepository @Inject constructor(private val fetchProfile: FetchProfile) {
    suspend fun fetchProfile(userId : String) : OtherProfile{
        return fetchProfile.fetchProfile(userId)
    }
}



