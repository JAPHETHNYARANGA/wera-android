package com.example.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.Listings
import com.example.wera.domain.useCase.GetUserListingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetUserListingsViewModel @Inject constructor(private val getUserListingsUseCase: GetUserListingsUseCase) : ViewModel() {
    private val _listings = MutableStateFlow(emptyList<Listings>())
    val listingsDisplay : MutableStateFlow<List<Listings>> get() = _listings

    private  val _isRefreshing = MutableStateFlow(false)

    val isRefreshing : StateFlow<Boolean> get() = _isRefreshing

    init {
        fetchListings()
    }

    fun fetchListings(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.value = true
                val listingData = getUserListingsUseCase.getUserListingsUseCase()
                val listings = listingData.listings
                _listings.value = listings
            }catch (e: Exception){
                Log.d("Failure fetching user listings", "${e.message}")
            }
        }
    }
}



