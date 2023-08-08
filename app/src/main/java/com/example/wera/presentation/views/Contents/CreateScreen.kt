package com.example.wera.presentation.views.Contents

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wera.domain.models.CategoriesData
import com.example.wera.presentation.viewModel.GetCategoriesViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.GetUserListingsViewModel
import com.example.wera.presentation.viewModel.PostItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FavoritesScreen(
    postItemViewModel: PostItemViewModel,
    navController: NavController,
    getListingsViewModel: GetListingsViewModel,
    getUserListingsViewModel : GetUserListingsViewModel,
    getCategoriesViewModel : GetCategoriesViewModel
){

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(0) }
    val context = LocalContext.current

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var isExpandedStatus by remember {
        mutableStateOf(false)
    }
    var status by remember{
        mutableStateOf(0)
    }

    var selectedCategory by remember { mutableStateOf<CategoriesData?>(null) }
    // Observe the categories data from the view model
    val categoriesData by getCategoriesViewModel.categoriesDisplay.collectAsState()

    fun statusToString(status: Int): String {
        return when (status) {
            1 -> "Job Creator"
            2 -> "Job Seeker"
            else -> "" // Handle the default case or any other status values you might have
        }
    }






    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {



        Text(
            text = "Status", // Add the label text here

            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            // Align the ExposedDropdownMenuBox to center
            ExposedDropdownMenuBox(
                expanded = isExpandedStatus,
                onExpandedChange = { isExpandedStatus = it },
                modifier = Modifier.align(Alignment.Center)
            ) {
                OutlinedTextField(
                    value = TextFieldValue(statusToString(status)),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedStatus)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpandedStatus,
                    onDismissRequest = { isExpandedStatus = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Job Creator") },
                        onClick = {
                            status = 1
                            isExpandedStatus = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Job Seeker") },
                        onClick = {
                            status = 2
                            isExpandedStatus = false
                        }
                    )
                }
            }
        }



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



        Text(
            text = "Categories", // Add the label text here

            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            // Align the ExposedDropdownMenuBox to center
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier = Modifier.align(Alignment.Center)
            ) {
                OutlinedTextField(
                    value = TextFieldValue(selectedCategory?.name ?: ""),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    // Populate the dropdown menu with categories data
                    categoriesData.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name ?: "") },
                            onClick = {
                                selectedCategory = category // Store the selected category data
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }


        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF1A202C),
                unfocusedBorderColor = Color(0xFF1A202C)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        )

        Button(
            onClick = {
                if(name.isNotEmpty() && description.isNotEmpty() && location.isNotEmpty() && category.toString().isNotEmpty() && amount.isNotEmpty()){
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            postItemViewModel.postItem(
                                name, description, location, amount, 2, status
                            ).let { response ->

                                Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()

                                if (response.success) {

                                    navController.navigate("home")
                                    getListingsViewModel.fetchListings()
                                    getUserListingsViewModel.fetchListings()


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
