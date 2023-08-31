package com.example.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.models.Listings
import com.example.wera.domain.models.UserProfile
import com.example.wera.domain.useCase.GetUserListingsUseCase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class GetUserListingsViewModel @Inject constructor(private val getUserListingsUseCase: GetUserListingsUseCase) : ViewModel() {
    private val _listings = MutableStateFlow(emptyList<Listings>())
    val listingsDisplay : MutableStateFlow<List<Listings>> get() = _listings

    private  val _isRefreshing = MutableStateFlow(false)

    val isRefreshing : StateFlow<Boolean> get() = _isRefreshing

    private val _imageUrl = MutableStateFlow<String?>(null)

    val imageUrl: StateFlow<String?> = _imageUrl

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

//                for (listing in listings) {
//                    listing.image?.let { storageLocation ->
//
//                        val storage = FirebaseStorage.getInstance()
//                        val storageRef = storage.getReference(storageLocation)
//                        val imageUrl = storageRef.downloadUrl.await().toString()
//                        _imageUrl.value = imageUrl
//
//
//                    }
//                }

            }catch (e: Exception){
                Log.d("Failure fetching user listings", "${e.message}")
            }
        }
    }
}



