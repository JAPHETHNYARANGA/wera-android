package com.example.wera.presentation.views.Contents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.wera.R
import com.example.wera.presentation.viewModel.MessagesViewModel

@Composable
fun MessagesScreen(messagesViewModel : MessagesViewModel) {
    val messages by messagesViewModel.showMessages.collectAsState()
    val context = LocalContext.current


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
//                color = Color.Gray,
            ) {
                Card(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column() {
                                Image(
                                    painter = painterResource(R.drawable.worker),
                                    contentDescription = "My Image",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                                Text(
                                    text = "Farmer",
                                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                )
                            }
                            Spacer(modifier = Modifier.width(30.dp))
                            Column() {
                                Text(text = message.message)
                                Spacer(modifier = Modifier.height(30.dp))
                                if (message.updated_at.isEmpty()) {
                                    Text(text = message.created_at , style = TextStyle(fontWeight = FontWeight.Bold))
                                } else {
                                    Text(text = message.updated_at, style = TextStyle(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}