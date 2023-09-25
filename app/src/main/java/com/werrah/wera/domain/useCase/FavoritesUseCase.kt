package com.werrah.wera.domain.useCase


import com.werrah.wera.domain.models.FavoritesResponse
import com.werrah.wera.domain.repository.AddToFavoritesRepository
import com.werrah.wera.domain.repository.RemoveFromFavoritesRepository
import javax.inject.Inject



class AddToFavoritesUseCase @Inject constructor(private val addToFavoritesRepository: AddToFavoritesRepository){
    suspend fun addToFavorites(id:Int?) :FavoritesResponse{
        return addToFavoritesRepository.addToFavorites(id)
    }
}

class RemoveFromFavoritesUseCase @Inject constructor(private val removeFromFavoritesRepository: RemoveFromFavoritesRepository){
    suspend fun removeFromFavorites(id:Int?) :FavoritesResponse{
        return removeFromFavoritesRepository.removeFromFavorites(id)
    }
}