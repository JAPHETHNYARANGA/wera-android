package com.werrah.wera.presentation.views.Contents


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.werrah.wera.R
import com.werrah.wera.presentation.viewModel.FavoritesViewModel
import com.werrah.wera.presentation.viewModel.GetIndividualItemViewModel
import com.werrah.wera.presentation.viewModel.MessagesViewModel
import com.werrah.wera.presentation.viewModel.RemoveFromFavoritesViewModel


@Composable
fun IndividualItemPage(
    navController: NavController,
    getIndividualItemViewModel: GetIndividualItemViewModel,
    messagesViewModel: MessagesViewModel,
    favoritesViewModel : FavoritesViewModel,
    removeFromFavoritesViewModel :RemoveFromFavoritesViewModel

){
    val context = LocalContext.current
    val itemDataState  = getIndividualItemViewModel.individualDisplay.collectAsState()
    val itemData = itemDataState.value
    val chatIdState = messagesViewModel.chatIdVal.collectAsState()
    val chatId = chatIdState.value
    val imageUrl by getIndividualItemViewModel.imageUrl.collectAsState()



    if (itemData != null && itemData.success == true) {
        Column(modifier = Modifier.padding(end = 10.dp, start = 10.dp)) {
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Wrap the first Button in a Box
                Box(
                    modifier = Modifier.weight(1f), // Adjust weight as needed to center horizontally
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                     //TODO

                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_eye_off),
                                contentDescription = "message Icon",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${itemData.listing?.request_count ?: 0}")
                        }
                    }
                }

                // Wrap the second Button in a Box
                Box(
                    modifier = Modifier.weight(1f), // Adjust weight as needed to center horizontally
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        //TODO
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (itemData.favorite == true){
                                Icon(
                                    painter = painterResource(id = R.drawable.favoritesliked),
                                    contentDescription = "contact Icon",
                                    modifier = Modifier.size(20.dp)
                                        .clickable { removeFromFavoritesViewModel.removeFromFavorites(
                                            itemData.listing?.id
                                        ) }
                                )
                            }else{
                                Icon(
                                    painter = painterResource(id = R.drawable.favorites),
                                    contentDescription = "contact Icon",
                                    modifier = Modifier.size(20.dp)
                                        .clickable { favoritesViewModel.addToFavorites(
                                            itemData.listing?.id
                                        ) }
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Favorite")
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


            imageUrl?.let { url ->
                val painter = rememberImagePainter(url)

                // Adjust the modifier for the Image to cover half the screen
                Image(
                    painter = painter,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // You can adjust the height as needed
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Add other Text components for other properties of the listing
            // For example:
            Text(text = "Description", style = TextStyle(fontWeight = FontWeight.Bold))
            Text(text = "${itemData.listing?.description}")
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Location", style = TextStyle(fontWeight = FontWeight.Bold))
            Text(text = "${itemData.listing?.Location}")

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Budget", style = TextStyle(fontWeight = FontWeight.Bold))
            Text(text = "${itemData.listing?.amount}")

            // Add other properties as needed

            Spacer(modifier = Modifier.height(16.dp))
            if(itemData.user?.userId != messagesViewModel.userId) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        val receiverId = itemData?.user?.userId
                        val senderId = messagesViewModel.userId
                        if (receiverId != null) {
                            messagesViewModel.getChatId(senderId, receiverId)
                        }

                        if (chatId != null) {
                            Log.d("ChatId data", chatId)
                        }
                        if (chatId != null) {
                            messagesViewModel.showIndividualMessage(chatId)
                        }

                        navController.navigate("createMessage/$receiverId")

                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.message), // Replace with your icon resource
                                contentDescription = "message Icon",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Send Message")
                        }
                    }


                    Button(onClick = {
                        val phoneNumber = itemData?.user?.phone ?: ""
                        if (phoneNumber.isNotBlank()) {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:$phoneNumber")
                            context.startActivity(intent)
                        }
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.phone), // Replace with your icon resource
                                contentDescription = "contact Icon",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Contact")
                        }
                    }


                }
            }
        }
    } else {
        // If itemData is null or does not contain the listing data, you can display a message
        Text(text = "Item data not available")
    }

    DisposableEffect(Unit) {
        onDispose {
            // Call the clearData function to clear data when the composable is disposed
            getIndividualItemViewModel.clearData()
        }
    }
}


