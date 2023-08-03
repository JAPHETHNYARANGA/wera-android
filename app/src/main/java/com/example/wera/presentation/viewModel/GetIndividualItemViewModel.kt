package com.example.wera.presentation.viewModel



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.models.IndividualListing
import com.example.wera.domain.models.UserProfile
import com.example.wera.domain.models.listingData
import com.example.wera.domain.useCase.GetIndividualItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetIndividualItemViewModel @Inject constructor(private val getIndividualItemUseCase: GetIndividualItemUseCase) : ViewModel() {
    private val _listing = MutableStateFlow<IndividualListing?>(null)
    val individualDisplay : StateFlow<IndividualListing?> = _listing
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing : StateFlow<Boolean> = _isRefreshing



    fun fetchIndividualItem(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isRefreshing.value = true
                val individualListing: IndividualListing = getIndividualItemUseCase.getIndividualItemUseCase(itemId)
                _listing.value = individualListing // Assign individualListing directly to _listing.value

            } catch (e: Exception) {
                Log.d("Failure fetching Individual item data", "${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}


