package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.ListingsData
import com.werrah.wera.domain.repository.GetUserListingsRepository
import javax.inject.Inject


class GetUserListingsUseCase @Inject constructor(private val getUserListingsRepository: GetUserListingsRepository){
    suspend fun getUserListingsUseCase(currentPage:Int) : ListingsData{
        return getUserListingsRepository.getUserListings(currentPage)
    }
}

