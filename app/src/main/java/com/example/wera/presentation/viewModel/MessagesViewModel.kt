package com.example.wera.presentation.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wera.domain.models.message
import com.example.wera.domain.useCase.GetMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(private val getMessagesUseCase: GetMessagesUseCase) : ViewModel(){
    private val _messages = MutableStateFlow(emptyList<message>())
    val showMessages : MutableStateFlow<List<message>> get() = _messages
    private val _isRefreshing = MutableStateFlow(false)

    val iRefreshing : StateFlow<Boolean> get() = _isRefreshing

    init {
        fetchMessages()
    }

    fun fetchMessages(){
        if (!_isRefreshing.value){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    val messagesData = getMessagesUseCase.getMessagesUseCase()
                    val messages = messagesData.messages
                    _messages.value = messages
                }catch (e: Exception){
                    Log.d("Failure Fetching messages", "${e.message}")
                }finally {
                    _isRefreshing.value = false
                }

            }
        }
    }

    fun refreshMessages(){
        fetchMessages()
    }
}


