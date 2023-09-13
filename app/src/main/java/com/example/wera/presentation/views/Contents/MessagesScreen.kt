package com.example.wera.presentation.views.Contents

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.wera.R
import com.example.wera.presentation.viewModel.MessagesViewModel
import com.example.wera.presentation.viewModel.UpdateProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    navController: NavController,
    messagesViewModel : MessagesViewModel,
    updateProfileViewModel : UpdateProfileViewModel
) {
    val messages by messagesViewModel.showMessages.collectAsState()
    val context = LocalContext.current
    val receiverId by messagesViewModel.receiverIdVal.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(messages.size) { index ->
            val message = messages[index]

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
            ) {
                Card(
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        val userId = messagesViewModel.userId
                        val chatId = message.chat_id
                        if (chatId != null) {
                            messagesViewModel.getReceiverId(userId, chatId)
                        }

                        message.chat_id?.let { messagesViewModel.showIndividualMessage(it) }

                        receiverId?.let { Log.d("receiverId", it) }

                        receiverId?.let { receiverId ->
                            navController.navigate("createMessage/$receiverId")
                        }
                    }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column() {
                                // Wrap the Image in a clickable composable
                                Image(
                                    painter = painterResource(R.drawable.worker),
                                    contentDescription = "My Image",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                           Toast.makeText(context, message.user?.userId , Toast.LENGTH_LONG).show()
                                            message.user?.userId?.let {
                                                updateProfileViewModel.fetchOtherProfile(
                                                    it
                                                )
                                            }
                                            navController.navigate("OtherProfile")
                                        }
                                )
                                Text(
                                    text = message.user?.name ?: "Unknown",
                                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                )
                            }
                            Spacer(modifier = Modifier.width(30.dp))
                            Column() {
                                message.message?.let { Text(text = it) }
                                Spacer(modifier = Modifier.height(30.dp))
                                if (message.updated_at?.isEmpty()!!) {
                                    message.created_at?.let { Text(text = it, style = TextStyle(fontWeight = FontWeight.Bold)) }
                                } else {
                                    message.updated_at?.let { Text(text = it, style = TextStyle(fontWeight = FontWeight.Bold)) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}