package com.example.wera.presentation.views.Contents


import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wera.MainActivity
import com.example.wera.R
import com.example.wera.navigation.BottomBarScreen
import com.example.wera.presentation.viewModel.GetIndividualItemViewModel
import com.example.wera.presentation.viewModel.GetUserListingsViewModel
import com.example.wera.presentation.viewModel.GetUserViewModel
import com.example.wera.presentation.views.Contents.Profile.CircularImage
import com.example.wera.presentation.views.Contents.Profile.clearToken


@Composable
fun IndividualItemPage(navController: NavController, sharedPreferences: SharedPreferences, getIndividualItemViewModel: GetIndividualItemViewModel){
    val context = LocalContext.current
    val itemDataState  = getIndividualItemViewModel.individualDisplay.collectAsState()
    val itemData = itemDataState.value

  
    if (itemData != null && itemData.success) {
        Column(modifier =  Modifier.padding(end = 10.dp, start = 10.dp)) {

            Image(painter = painterResource(id = R.drawable.worker), contentDescription = "Icon Image")
            
            Spacer(modifier = Modifier.height(30.dp))


            // Add other Text components for other properties of the listing
            // For example:
            Text(text = "Description: ${itemData.listing.description}")
            Text(text = "Location: ${itemData.listing.Location}")
            Text(text = "Category: ${itemData.listing.category}")
            Text(text = "Amount: ${itemData.listing.amount}")
            // Add other properties as needed
        }
    } else {
        // If itemData is null or does not contain the listing data, you can display a message
        Text(text = "Item data not available")
    }
}


