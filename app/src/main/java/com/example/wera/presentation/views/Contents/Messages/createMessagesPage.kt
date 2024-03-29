package com.example.wera.presentation.views.Contents.Messages

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wera.MainActivity
import com.example.wera.presentation.viewModel.MessagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun createMessagesPage(navController: NavController, messagesViewModel: MessagesViewModel) {
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current
    val senderId = messagesViewModel.userId

    val receiverId =
        remember {
        val navBackStackEntry = navController.currentBackStackEntry
        val arguments = navBackStackEntry?.arguments
        arguments?.getString("receiverId")
    }


    //clear messages each time a person leaves the create message screen
    DisposableEffect(Unit) {
        messagesViewModel.fetchMessages()
        onDispose {
            messagesViewModel.clearIndividualMessages()
        }
    }

    Column(
        modifier = Modifier
            .padding(end = 15.dp, start = 15.dp, bottom = 30.dp)
            .fillMaxSize()
    ) {
        LazyColumn (
            modifier = Modifier
                .weight(1f)
                ){
            items(messagesViewModel.showIndividualMessages.value) { individualMessage ->
                val isCurrentUser = individualMessage.sender_id == messagesViewModel.userId
                val cardColor = if (isCurrentUser) {
                    Color.LightGray // Change this to the color you want for the current user's messages
                } else {
                    Color.White // Change this to the color you want for other users' messages
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
//                    contentAlignment = if (isCurrentUser) Alignment.End else Alignment.Start
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(cardColor) // Set the background color here
                        ) {
                            // Display the individual message here
                            Text(
                                text = individualMessage.message ?: "No message",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }



        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = message,
            onValueChange = { message = it },
            label = { Text(text = "Message") },
            placeholder = { Text(text = "Enter Message") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
//                messagesViewModel.postMessage("hello", "123", "456")
                if(message.isNotEmpty()){
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            receiverId?.let {
                                messagesViewModel.postMessage(
                                    message, senderId, it
                                ).let { response ->
                                    if (response.success) {
                                        Toast.makeText(context, "Message sent successfully", Toast.LENGTH_LONG).show()

                                    } else {
                                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                            Log.d("Login","${e.message}")
                            // You can also log the exception for debugging purposes
                            e.printStackTrace()
                        }finally {
                            message = ""
                            messagesViewModel.fetchMessages()
                            messagesViewModel.showIndividualMessages
                        }
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Send Message")
        }

    }
    Column() {
        Spacer(modifier = Modifier.height(30.dp))
    }
}
