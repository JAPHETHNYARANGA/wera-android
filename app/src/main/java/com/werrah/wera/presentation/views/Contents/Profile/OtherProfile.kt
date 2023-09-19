package com.werrah.wera.presentation.views.Contents.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.werrah.wera.R
import com.werrah.wera.presentation.viewModel.UpdateProfileViewModel




@Composable
fun OtherProfile(navController: NavController, updateProfileViewModel: UpdateProfileViewModel) {
    // Observe the user or other user profile data using collectAsState
    val userProfileState = updateProfileViewModel.profileDisplay.collectAsState()

    val imageUrl by updateProfileViewModel.imageUrl.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top quarter (image)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Gray) // Background color for illustration
        ) {
            if (imageUrl != null) {
                val painter = rememberImagePainter(imageUrl)
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Display a placeholder image or an error message
                // Example:
                Image(
                    painter = painterResource(id = R.drawable.worker),
                    contentDescription = "Placeholder Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom three-quarters (profile info)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .background(Color.White) // Background color for illustration
        ) {

            Text(text = "${userProfileState.value?.user?.rating ?: "No rating found"}",)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "${userProfileState.value?.user?.name}", style = TextStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp))
            // Add more fields as needed
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "${userProfileState.value?.user?.bio}", )

        }
    }
}