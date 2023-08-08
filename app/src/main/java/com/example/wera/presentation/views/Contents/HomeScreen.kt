package com.example.wera.presentation.views.Contents

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wera.R
import com.example.wera.domain.models.UserData
import com.example.wera.presentation.viewModel.GetIndividualItemViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, getListingsViewModel: GetListingsViewModel,getIndividualItemViewModel: GetIndividualItemViewModel) {
    val listings by getListingsViewModel.listingsDisplay.collectAsState()
    val isSingleItem = listings.size == 1 // Check if there is only one item in the list
    val context = LocalContext.current

    fun onCardClicked(itemId:Int){
        getIndividualItemViewModel.fetchIndividualItem(itemId)
        Toast.makeText(context, "$itemId", Toast.LENGTH_SHORT).show()
        navController.navigate("individualItem")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listings.size) { index ->
            val process = listings[index]

            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(modifier = Modifier.weight(1f), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                            .clickable { process.id?.let { onCardClicked(it) } },
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.worker), contentDescription = "Icon Image")
                        Text(text = "Name",style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                        process.name?.let { Text(text = it) }
                        Text(text = "Description", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                        process.description?.let { Text(text = it) }
                        Text(text = "Category", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                        process.category?.let { Text(text = it) }
                        Text(text = "Amount", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                        process.amount?.let { Text(text = it) }
                        // Add other content for the card here
                    }
                }
                // Display the second card only if there is more than one item in the list
                if (!isSingleItem && index + 1 < listings.size) {
                    val process2 = listings[index + 1]
                    Card(modifier = Modifier.weight(1f), elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                                .clickable { process2.id?.let { onCardClicked(it) } },
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(painter = painterResource(id = R.drawable.worker), contentDescription = "Icon Image")
                            Text(text = "Name", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                            process2.name?.let { Text(text = it) }
                            Text(text = "Description", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                            process2.description?.let { Text(text = it) }
                            Text(text = "Category", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                            process2.category?.let { Text(text = it) }
                            Text(text = "Amount", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp))
                            process2.amount?.let { Text(text = it) }
                            // Add content for the second card here
                        }
                    }
                }
            }
        }
    }
}
