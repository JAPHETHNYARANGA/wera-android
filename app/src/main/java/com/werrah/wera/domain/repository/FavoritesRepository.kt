package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.AddToFavorites
import com.werrah.wera.data.network.RemoveFromFavorites
import com.werrah.wera.domain.models.FavoritesResponse
import javax.inject.Inject


class AddToFavoritesRepository @Inject constructor(private val addToFavorites: AddToFavorites){
    suspend fun addToFavorites(id:Int?): FavoritesResponse {
        return addToFavorites.favorites(id)
    }
}

class RemoveFromFavoritesRepository @Inject constructor(private val removeFromFavorites: RemoveFromFavorites){
    suspend fun removeFromFavorites(id:Int?): FavoritesResponse {
        return removeFromFavorites.favorites(id)
    }
}