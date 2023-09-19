package com.werrah.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.domain.models.DeleteListingResponse
import com.werrah.wera.domain.useCase.DeleteListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeleteListingViewModel @Inject constructor(private val deleteListingUseCase: DeleteListingUseCase) : ViewModel() {
    private val _deleteResult = MutableStateFlow<DeleteListingResponse?>(null)
    val deleteResult: StateFlow<DeleteListingResponse?> = _deleteResult

    fun deleteListing(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result: DeleteListingResponse = deleteListingUseCase.deleteListing(itemId)
                _deleteResult.value = result
            } catch (e: Exception) {
                Log.d("Failure deleting listing", "${e.message}")
                _deleteResult.value = DeleteListingResponse(false, "Failed to delete listing")
            }
        }
    }

}

