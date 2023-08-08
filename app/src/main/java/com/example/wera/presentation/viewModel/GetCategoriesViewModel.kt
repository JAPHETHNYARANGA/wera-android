package com.example.wera.presentation.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.Categories
import com.example.wera.domain.models.CategoriesData
import com.example.wera.domain.useCase.GetCategoriesDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetCategoriesViewModel @Inject constructor(private val getCategoriesDataUseCase: GetCategoriesDataUseCase) : ViewModel() {
    private val _categories = MutableStateFlow(emptyList<CategoriesData>())
    val categoriesDisplay : MutableStateFlow<List<CategoriesData>> get() = _categories
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    init {
        fetchCategories()
    }

    fun fetchCategories(){
        if (!_isRefreshing.value){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    val categoryData = getCategoriesDataUseCase.getCategoriesUseCase()
                    val categories = categoryData.listings
                    _categories.value = categories

                } catch (e: Exception) {
                    Log.d("Failure fetching categories", "${e.message}")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }
        // Function to explicitly trigger refreshing the listings
    fun refreshListings() {
        fetchCategories()
    }
}

