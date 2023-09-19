package com.werrah.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.domain.models.Listings
import com.werrah.wera.domain.useCase.GetListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetListingsViewModel @Inject constructor(private val getListingUseCase: GetListingUseCase) : ViewModel() {
    private val _listings = MutableStateFlow(emptyList<Listings>())
    val listingsDisplay: MutableStateFlow<List<Listings>> get() = _listings
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    init {
        fetchListings()
    }

    fun fetchListings() {
        if (!_isRefreshing.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    val listingData = getListingUseCase.getListingUseCase()
                    val listings = listingData.listings
                    _listings.value = listings
                } catch (e: Exception) {
                    Log.d("Failure fetching listings", "${e.message}")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    // Function to explicitly trigger refreshing the listings
    fun refreshListings() {
        fetchListings()
    }
}
