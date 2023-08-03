package com.example.wera.presentation.views.Contents

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.LoginUserViewModel
import com.example.wera.presentation.viewModel.PostItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FavoritesScreen(
    postItemViewModel: PostItemViewModel,
    navController: NavController,
    getListingsViewModel: GetListingsViewModel
){

    val focusRequester = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val context = LocalContext.current


    Column(modifier =  Modifier.fillMaxSize()) {


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1A202C),
                unfocusedBorderColor = Color(0xFF1A202C)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1A202C),
                unfocusedBorderColor = Color(0xFF1A202C)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )


        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1A202C),
                unfocusedBorderColor = Color(0xFF1A202C)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )


        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1A202C),
                unfocusedBorderColor = Color(0xFF1A202C)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)


        )


        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1A202C),
                unfocusedBorderColor = Color(0xFF1A202C)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )




        Button(
            onClick = {
                if(name.isNotEmpty() && description.isNotEmpty() && location.isNotEmpty() && category.isNotEmpty() && amount.isNotEmpty()){
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            postItemViewModel.postItem(
                                name, description, location, amount, category
                            )?.let { response ->
                                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()

                                if (response.success) {

                                    navController.navigate("home")
                                    getListingsViewModel.fetchListings()

                                } else {
                                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                            Log.d("Login","${e.message}")
                            // You can also log the exception for debugging purposes
                            e.printStackTrace()
                        }
                    }
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
//            colors = ButtonDefaults.buttonColors(
//                contentColor = Color.White, // Set the text color to white
//                containerColor = Color(0xFF1A202C)
//            )
        ) {
            Text(text = "Create")
        }
    }
}