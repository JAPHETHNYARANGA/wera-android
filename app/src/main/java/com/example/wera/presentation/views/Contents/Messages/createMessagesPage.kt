package com.example.wera.presentation.views.Contents.Messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun createMessagesPage(navController : NavController){
        val messages = remember { mutableStateListOf<Message>() }
        val currentMessageState = remember { mutableStateOf(TextFieldValue()) }
        val content = LocalContext.current

    val arguments = navController.currentBackStackEntry?.arguments
    val messageId = arguments?.getString("messageId")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
//            MessageList(messages)
//            Spacer(modifier = Modifier.height(16.dp))
//            MessageInput(
//                currentMessageState = currentMessageState,
//                onMessageSend = { message ->
//                    messages.add(Message("You", message))
//                    currentMessageState.value = TextFieldValue() // Clear input
//                }
//            )
            if (messageId != null) {
                Text(text = messageId)
            }else{
                Text(text = "No message Id")
            }
        }
    }

    @Composable
    fun MessageList(messages: List<Message>) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            reverseLayout = true // To display messages from bottom to top
        ) {
            items(messages) { message ->
                MessageItem(message)
            }
        }
    }


fun items(messages: List<Message>, itemContent: @Composable() (LazyItemScope.(index: Int) -> Unit)) {
    TODO("Not yet implemented")
}

@Composable
    fun MessageItem(message: Int) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = message.toString(), textAlign = TextAlign.Start)
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun MessageInput(
        currentMessageState: MutableState<TextFieldValue>,
        onMessageSend: (String) -> Unit
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = currentMessageState.value,
                onValueChange = { newValue ->
                    currentMessageState.value = newValue
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onMessageSend(currentMessageState.value.text)
                        currentMessageState.value = TextFieldValue() // Clear input
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                onMessageSend(currentMessageState.value.text)
                currentMessageState.value = TextFieldValue() // Clear input
            }) {
                Text(text = "Send")
            }
        }
    }

    data class Message(val sender: String, val content: String)

