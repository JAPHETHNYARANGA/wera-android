package com.werrah.wera.presentation.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.data.sharedPreferencesLiveData.SharedPreferencesLiveData
import com.werrah.wera.domain.models.Listings
import com.werrah.wera.domain.useCase.GetListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetListingsViewModel @Inject constructor(
    private val getListingUseCase: GetListingUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _listings = MutableStateFlow(emptyList<Listings>())
    val listingsDisplay: MutableStateFlow<List<Listings>> get() = _listings
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val sharedPreferencesLiveData = SharedPreferencesLiveData(sharedPreferences)

    init {
        // Observe changes in SharedPreferences
        sharedPreferencesLiveData.observeForever {
            // Refresh data when SharedPreferences change
            refreshListings()
        }

        // Initial data fetch
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