package com.example.wera.presentation.viewModel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.models.IndividualListing
import com.example.wera.domain.models.UserProfile
import com.example.wera.domain.models.listingData
import com.example.wera.domain.useCase.GetIndividualItemUseCase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class GetIndividualItemViewModel @Inject constructor(private val getIndividualItemUseCase: GetIndividualItemUseCase) : ViewModel() {
    private val _listing = MutableStateFlow<IndividualListing?>(null)
    val individualDisplay : StateFlow<IndividualListing?> = _listing
    private val _isRefreshing = MutableStateFlow(false)

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    val isRefreshing : StateFlow<Boolean> = _isRefreshing



    fun fetchIndividualItem(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.value = true
                val individualListing: IndividualListing = getIndividualItemUseCase.getIndividualItemUseCase(itemId)
                _listing.value = individualListing // Assign individualListing directly to _listing.value

                individualListing.listing?.image.let { storageLocation ->
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storageLocation?.let { storage.getReference(it) }
                    val imageUrl = storageRef?.downloadUrl?.await().toString()
                    _imageUrl.value = imageUrl
                }

            } catch (e: Exception) {
                Log.d("Failure fetching Individual item data", "${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}


