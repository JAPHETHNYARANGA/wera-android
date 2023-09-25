package com.werrah.wera.presentation.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.domain.models.FavoritesResponse
import com.werrah.wera.domain.models.IndividualListing
import com.werrah.wera.domain.useCase.AddToFavoritesUseCase
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
                val addToFavorites = addToFavoritesUseCase.addToFavorites(id)



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
                val removeFromFavorites = removeFromFavoritesUseCase.removeFromFavorites(id)


            } catch (e: Exception) {
                Log.d("Failure fetching Individual item data", "${e.message}")
            }
        }
    }


}


