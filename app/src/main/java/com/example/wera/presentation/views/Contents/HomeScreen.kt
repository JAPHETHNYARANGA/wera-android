package com.example.wera.presentation.views.Contents

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.wera.R
import com.example.wera.domain.models.UserData
import com.example.wera.navigation.TopNabBar
import com.example.wera.presentation.viewModel.GetIndividualItemViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController,
               getListingsViewModel: GetListingsViewModel,
               getIndividualItemViewModel: GetIndividualItemViewModel) {
    val listings by getListingsViewModel.listingsDisplay.collectAsState()


    val context = LocalContext.current
    val isRefreshing by getListingsViewModel.isRefreshing.collectAsState(false)
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing) // Use the isRefreshing property from the view model

    fun onCardClicked(itemId: Int) {
        getIndividualItemViewModel.fetchIndividualItem(itemId)
        Toast.makeText(context, "$itemId", Toast.LENGTH_SHORT).show()
        navController.navigate("individualItem")
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .zIndex(10f)) {
        TopNabBar()
    }




    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            // Trigger the refresh action here
            Log.d("SwipeRefresh", "Refreshing...")
            getListingsViewModel.refreshListings()
        },
        content = {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2)
            ) {

                items(listings.size) { index ->
                    val process = listings[index]
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable { process.id?.let { onCardClicked(it) } },
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                val url = process?.image
                                val imageUrlState = remember { mutableStateOf<String?>(null) }

                                LaunchedEffect(url) {
                                    val storage = FirebaseStorage.getInstance()
                                    val storageRef = url?.let { storage.getReference(it) }

                                    try {
                                        val imageUrl = storageRef?.downloadUrl?.await()?.toString()
                                        imageUrlState.value = imageUrl
                                        Log.d("GetUserListingsViewModel", "Fetching image URL for storage location: $imageUrl")
                                    } catch (e: Exception) {
                                        // Handle any exceptions that may occur while fetching the image URL
                                        Log.e("GetUserListingsViewModel", "Error fetching image URL: ${e.message}")
                                    }
                                }

                                Image(
                                    painter = imageUrlState.value?.let {
                                        rememberImagePainter(it)
                                    } ?: painterResource(id = R.drawable.worker), // Use default image if imageUrl is null or empty
                                    contentDescription = "Listing Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                )


                                Text(
                                    text = "Task Name",
                                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                )
                                process.name?.let { Text(text = it) }
                                Text(
                                    text = "Description",
                                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                )

                                val description = process.description?.take(40)
                                val displayDescription = if ((process.description?.length
                                        ?: 0) > 40
                                ) {
                                    "$description..."
                                } else {
                                    description
                                }

                                Text(
                                    text = displayDescription.orEmpty(), // Use `orEmpty` to handle null descriptions
                                    style = TextStyle(fontSize = 12.sp)
                                )


                                Text(
                                    text = "Budget",
                                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                )
                                process.amount?.let { Text(text = it) }
                                // Add other content for the card here
                            }
                        }

                    }
                }
            }
        }
    )
}

