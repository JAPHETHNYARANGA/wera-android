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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wera.R

@Composable
fun MessagesScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        repeat(10) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                color = Color.Gray,

            ) {
                Card(
                    modifier = Modifier.padding(10.dp)
                ) {
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
                        
                        Spacer(modifier = Modifier.width(10.dp))
                       Column() {
                            Text(text = "If you have any questions or need any assistance, feel free to reach out to me.\n" +
                                    "\n" +
                                    "Thank you!")
                        }

                    }
                }
                // Placeholder content
            }
        }

    }

}