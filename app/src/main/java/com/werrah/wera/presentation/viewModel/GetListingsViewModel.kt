package com.werrah.wera.presentation.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.werrah.wera.data.room.Database.WeraDatabase
import com.werrah.wera.data.sharedPreferencesLiveData.SharedPreferencesLiveData
import com.werrah.wera.domain.models.Listings
import com.werrah.wera.domain.useCase.GetListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GetListingsViewModel @Inject constructor(
    private val getListingUseCase: GetListingUseCase,
    private val sharedPreferences: SharedPreferences,
//    private val database : WeraDatabase
) : ViewModel() {

    private var currentPage = 1
    private var totalPages = 1

//    private val database = Room.databaseBuilder(
//        appContext.applicationContext,
//        WeraDatabase::class.java,
//        "wera_database"
//    ).build()


//    override fun onCleared() {
//        super.onCleared()
//        database.close()
//    }

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
        if (!_isRefreshing.value && currentPage <= totalPages) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    val listingData = getListingUseCase.getListingUseCase(currentPage)
                    val listings = listingData.listings.data
                    _listings.value = _listings.value + listings // Append new data to the existing list

                    // Update pagination information
                    currentPage = listingData.listings.current_page + 1
                    totalPages = listingData.listings.last_page

                    // Insert listings into the Room database
//                    database.listingsDao().insertListings(listingData.listings.data)
                    // Fetch listings from the database
//                    val listingsFromDb = database.listingsDao().getAllListings().first()
//                    _listings.value = listingsFromDb
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