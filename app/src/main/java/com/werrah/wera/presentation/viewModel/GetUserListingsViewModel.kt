package com.werrah.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.domain.models.Listings
import com.werrah.wera.domain.useCase.GetUserListingsUseCase
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

    private val _imageUrl = MutableStateFlow<String?>(null)

    val imageUrl: StateFlow<String?> = _imageUrl

    private var currentPage = 1
    private var totalPages = 1

    init {
        fetchListings()
    }

    fun fetchListings(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.value = true
//                val listingData = getUserListingsUseCase.getUserListingsUseCase()
//                val listings = listingData.listings
//                _listings.value = listings.data

                val listingData = getUserListingsUseCase.getUserListingsUseCase(currentPage)
                val listings = listingData.listings.data
                _listings.value = _listings.value + listings // Append new data to the existing list

                // Update pagination information
                currentPage = listingData.listings.current_page + 1
                totalPages = listingData.listings.last_page

            }catch (e: Exception){
                Log.d("Failure fetching user listings", "${e.message}")
            }
        }
    }

    fun loadMoreListings() {
        if (currentPage <= totalPages) {
            fetchListings()
        }
    }
}



