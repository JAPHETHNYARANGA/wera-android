package com.example.wera.navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wera.R

@Composable
fun TopNabBar() {

    var context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF1A202C))
                .padding(end = 5.dp, start = 5.dp)
            ,

            horizontalArrangement = Arrangement.SpaceBetween, // Horizontal alignment
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-aligned items
            Text(text = "Werrah", color = Color.White)

            // Centered search area
//            BasicTextField(
//                value = "", // Pass the actual search value here
//                onValueChange = { /* Handle search value change */ },
//                modifier = Modifier
//                    .weight(1f) // Expand to fill available space
//                    .padding(horizontal = 16.dp)
//                    .height(40.dp) // Adjust the height as needed
//                    .background(Color.White) // Adjust the background color
//            )

            // Right-aligned icon



            Box(
                modifier = Modifier.clickable {
                    // Show the filter dialog when the filter icon is clicked
//                    Toast.makeText(context,"filterClicked", Toast.LENGTH_LONG).show()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White,
                )
            }


        }
    }
//}
