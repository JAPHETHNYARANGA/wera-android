package com.werrah.wera.presentation.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.domain.models.Favorites
import com.werrah.wera.domain.models.FavoritesList
import com.werrah.wera.domain.models.FavoritesResponse
import com.werrah.wera.domain.models.Listings
import com.werrah.wera.domain.useCase.AddToFavoritesUseCase
import com.werrah.wera.domain.useCase.GetFavoritesUseCase
import com.werrah.wera.domain.useCase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val addToFavoritesUseCase: AddToFavoritesUseCase): ViewModel() {
    private val _favorite = MutableStateFlow<FavoritesResponse?>(null)
    val favoriteDisplay: StateFlow<FavoritesResponse?> = _favorite


    fun addToFavorites(id:Int?){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                 addToFavoritesUseCase.addToFavorites(id)



            } catch (e: Exception) {
            Log.d("Failure fetching Individual item data", "${e.message}")
            }
        }
    }

}


@HiltViewModel
class RemoveFromFavoritesViewModel @Inject constructor(private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase): ViewModel() {
    private val _favorite = MutableStateFlow<FavoritesResponse?>(null)
    val favoriteDisplay: StateFlow<FavoritesResponse?> = _favorite


    fun removeFromFavorites(id:Int?){
        viewModelScope.launch(Dispatchers.IO) {
            try {
               removeFromFavoritesUseCase.removeFromFavorites(id)


            } catch (e: Exception) {
                Log.d("Failure fetching Individual item data", "${e.message}")
            }
        }
    }
}

@HiltViewModel
class FetchFavoritesViewModel @Inject constructor(private val getFavoritesUseCase: GetFavoritesUseCase): ViewModel() {

    private val _favorites = MutableStateFlow(emptyList<FavoritesList>())
    val favoritesDisplay: MutableStateFlow<List<FavoritesList>> get() = _favorites
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing


    init {
        fetchFavorites()
    }

    fun fetchFavorites(){
        if (!_isRefreshing.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    val favoritesData = getFavoritesUseCase.getFavorites()
                    val favorites = favoritesData.listings
                    _favorites.value = favorites
                    Log.d("Favorites", "$favoritesData")

                } catch (e: Exception) {
                    Log.d("Failure fetching favorites", "${e.message}")
                } finally {
                    _isRefreshing.value = false
                }

            }
        }
    }

    fun refreshListings() {
        fetchFavorites()
    }

}
