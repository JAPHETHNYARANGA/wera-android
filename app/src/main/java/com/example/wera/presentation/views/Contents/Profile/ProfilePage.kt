package com.example.wera.presentation.views.Contents.Profile

import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.wera.MainActivity
import com.example.wera.R
import com.example.wera.navigation.BottomBarScreen
import com.example.wera.presentation.viewModel.DeleteAccountViewModel
import com.example.wera.presentation.viewModel.DeleteListingViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.GetUserListingsViewModel
import com.example.wera.presentation.viewModel.GetUserViewModel
import com.example.wera.presentation.viewModel.LogoutViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfilePage(navController: NavController, sharedPreferences: SharedPreferences, getUserViewModel: GetUserViewModel, getUserListingsViewModel: GetUserListingsViewModel,
deleteListingViewModel: DeleteListingViewModel, getListingsViewModel:GetListingsViewModel, logoutViewModel : LogoutViewModel, deleteAccountViewModel: DeleteAccountViewModel){

    val context = LocalContext.current
    val userDataState = getUserViewModel.userDisplay.collectAsState()

    val userData = userDataState.value // Get the current value of the user data state
    val listings by getUserListingsViewModel.listingsDisplay.collectAsState()
    val showDialog = remember { mutableStateOf(false) }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp)
        .verticalScroll(
            rememberScrollState()
        )
    ) {

        Row(modifier = Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .fillMaxWidth() , horizontalArrangement =  Arrangement.Center) {
            CircularImage(painter = painterResource(id = R.drawable.worker), contentDescription = "Profile Image")
        }


        // Display the user data attributes here
        userData?.name?.let {
            Text(text = it, style = TextStyle(fontWeight = FontWeight.Bold))
        }
        userData?.email?.let {
            Text(text = it, style = TextStyle(fontWeight = FontWeight.Bold))
        }
        Spacer(modifier = Modifier.height(5.dp))
        userData?.occupation?.let {
            Text(text = it, style = TextStyle(fontWeight = FontWeight.Bold))
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Bio", style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(5.dp))
        userData?.bio?.let {
            Text(text = it)
        }



        Spacer(modifier = Modifier.height(65.dp))

        //horizontally scrolling columns
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listings) { process ->
                Card(
                    modifier = Modifier.padding(5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id = R.drawable.worker), contentDescription = "Icon Image",  modifier = Modifier.size(50.dp))
                        process.name?.let {
                            Text(
                                text = it,
                                fontStyle = FontStyle.Normal,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        process.category?.let {
                            Text(
                                text = it,
                                fontStyle = FontStyle.Normal,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        process.description?.let {
                            Text(
                                text = it,
                                fontStyle = FontStyle.Normal,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                IconButton(onClick = { /* Handle Edit button click here */ }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                            }
                            Column {
                                IconButton(onClick = {
                                    showDialog.value = true


                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }

                                if (showDialog.value) {
                                    AlertDialog(
                                        onDismissRequest = { showDialog.value = false }, // Dismiss the dialog
                                        title = { Text(text = "Do you want to delete? ${process.name}") },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    // Handle the "Yes" option
                                                    showDialog.value = false // Dismiss the dialog

                                                    // Perform the checkout operation here

                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        process.id?.let {
                                                            deleteListingViewModel.deleteListing(it)?.let { response ->
                                                                // Handle the response
                                                                Toast.makeText(context, "${process.name} deleted successfully", Toast.LENGTH_LONG).show()

                                                            }
                                                        }
                                                    }
                                                    getUserListingsViewModel.fetchListings()
                                                    getListingsViewModel.fetchListings()
                                                },
                                                colors = ButtonDefaults.textButtonColors(
//                                                contentColor = MaterialTheme.colors.primary
                                                )
                                            ) {
                                                Text(text = "Yes")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = { showDialog.value = false }, // Dismiss the dialog
                                                colors = ButtonDefaults.textButtonColors(
//                                                contentColor = MaterialTheme.colors.primary
                                                )
                                            ) {
                                                Text(text = "No")
                                            }
                                        },
                                        properties = DialogProperties(usePlatformDefaultWidth = false)
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(65.dp))

        Column() {
                Button(modifier = Modifier.fillMaxWidth(),onClick = {

                    navController.navigate( BottomBarScreen.Edit.route)
                }) {
                    Text(text = "Edit")

                }

                Spacer(modifier =Modifier.height(10.dp))

                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        val response = logoutViewModel.logoutUser()
                        if (response.isSuccessful) {
                            clearToken(sharedPreferences)

                            // Add any additional logic here, such as navigating to the login screen
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            // Handle error, if any
                            Toast.makeText(context, "Logout failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(text = "Logout")
                }

                Spacer(modifier =Modifier.height(10.dp))
                Button( modifier = Modifier.fillMaxWidth(),onClick = {

                    CoroutineScope(Dispatchers.Main).launch {
                        val response = deleteAccountViewModel.deleteAccount()
                        if (response.isSuccessful){
                            clearToken(sharedPreferences)

                            // Add any additional logic here, such as navigating to the login screen
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            Toast.makeText(context, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "delete Account failed", Toast.LENGTH_SHORT).show()
                        }
                    }


                }) {
                    Text(text = "Delete Account")

                }
            }
    }
}

fun clearToken(sharedPreferences: SharedPreferences) {
    sharedPreferences.edit().remove("loginPreference").apply()
}

@Composable
fun CircularImage(painter: Painter, contentDescription: String) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape) // Clip the image to make it circular
    )
}

