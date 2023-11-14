package com.werrah.wera.presentation.viewModel


import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.werrah.wera.domain.models.PostMessageResponse
import com.werrah.wera.domain.models.message
import com.werrah.wera.domain.useCase.GetChatIdUseCase
import com.werrah.wera.domain.useCase.GetMessagesUseCase
import com.werrah.wera.domain.useCase.GetReceiverIdUseCase
import com.werrah.wera.domain.useCase.GetSpecificMessageUseCase
import com.werrah.wera.domain.useCase.PostMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val getSpecificMessageUseCase: GetSpecificMessageUseCase,
    private val postMessageUseCase: PostMessageUseCase,
    private val sharedPreferences: SharedPreferences,
    private val getChatIdUseCase: GetChatIdUseCase,
    private val getReceiverIdUseCase: GetReceiverIdUseCase
) : ViewModel(){
    private val _messages = MutableStateFlow(emptyList<message>())
    val showMessages : MutableStateFlow<List<message>> get() = _messages
    private val _isRefreshing = MutableStateFlow(false)



    private val _individualMessage = MutableStateFlow(emptyList<message>())
    val showIndividualMessages : MutableStateFlow<List<message>> get() = _individualMessage

    private val _postMessage = MutableLiveData<PostMessageResponse>()

    val userId = sharedPreferences.getString("userIdPreference", "") ?: ""

    private val _chatId = MutableStateFlow<String?>(null)
    val chatIdVal: StateFlow<String?> = _chatId

    private val _receiverId = MutableStateFlow<String?>(null)
    val receiverIdVal : StateFlow<String?> = _receiverId
    val postMessage: LiveData<PostMessageResponse> get() = _postMessage

    val iRefreshing : StateFlow<Boolean> get() = _isRefreshing

    val imageUrlLiveData: LiveData<String> = MutableLiveData()



    init {
        fetchMessages()
    }

    fun fetchMessages(){
        if (!_isRefreshing.value){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    // After retrieving the userId from SharedPreferences
                    val userId = sharedPreferences.getString("userIdPreference", "") ?: ""

                    // Make the network request using the userId as a query parameter
                    val messagesData = getMessagesUseCase.getMessagesUseCase(userId)
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

    fun showIndividualMessage(chatId: String) {
        if (!_isRefreshing.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _isRefreshing.value = true
                    val messagesData = getSpecificMessageUseCase.getSpecificMessageUseCase(chatId)
                    val messages = messagesData.messages
                    _individualMessage.value = messages // Update the individual message StateFlow
                    Log.d("Individual Message", "${messages}")
                } catch (e: Exception) {
                    Log.d("Failure Fetching messages", "${e.message}")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun getChatId(senderId:String, receiverId:String){
        if (!_isRefreshing.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val chatId = getChatIdUseCase.getChatId(senderId, receiverId)
                    val chatIdData = chatId.chat_id
                    _chatId.value = chatIdData
                } catch (e: Exception) {
                    Log.d("Failure Fetching ChatId", "${e.message}")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun getReceiverId(userId:String, chatId: String){
        if(!_isRefreshing.value){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val receiverId = getReceiverIdUseCase.getReceiverId(userId, chatId)
                    val receiverIdData = receiverId.receiver_id
                    _receiverId.value = receiverIdData
                } catch (e: Exception) {
                    Log.d("Failure Fetching ReceiverId", "${e.message}")
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    suspend fun postMessage(
        message : String, senderId:String, receiverId:String
    ) : PostMessageResponse{
        try{
            return postMessageUseCase.postMessage(message, senderId, receiverId)
        } catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw MessageException("Request timed out")
        }
    }


    fun refreshMessages(chatId: String){
        fetchMessages()
        showIndividualMessage(chatId)
    }

    // Function to clear individual messages
    fun clearIndividualMessages() {
        _individualMessage.value = emptyList() // Clear the individual messages list
    }

    // Function to perform cleanup when the composable is no longer needed
    fun onDispose() {
        // Add any additional cleanup logic here
        clearIndividualMessages()
    }
}
class MessageException(message: String) : Exception(message)


