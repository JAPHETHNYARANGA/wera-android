package com.example.wera.presentation.views.Contents

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wera.R
import com.example.wera.domain.models.CategoriesData
import com.example.wera.presentation.viewModel.GetCategoriesViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.GetUserListingsViewModel
import com.example.wera.presentation.viewModel.PostItemViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.UUID


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

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }


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


        Row(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                // Show the image with the "+" icon below it


                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        ItemImage(
                            bitmap = btm,
                            contentDescription = null,
                            modifier = Modifier // Provide the correct type of the modifier here
                        )
                    }
                }



                Spacer(modifier = Modifier.height(10.dp))
                IconButton(
                    onClick = {
                        launcher.launch("image/*")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add Image"
                    )
                }
            }
        }



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

                    CoroutineScope(Dispatchers.Main).launch {

                        try {
                            // Get a reference to Firebase Storage
                            val selectedFile = imageUri?.let { uri ->
                                try {
                                    val inputStream = context.contentResolver.openInputStream(uri)
                                    val byteArray = inputStream?.readBytes()
                                    inputStream?.close()

                                    if (byteArray != null) {
                                        // Save the ByteArray to a temporary file
                                        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
                                        tempFile.writeBytes(byteArray)
                                        tempFile
                                    } else {
                                        null
                                    }
                                } catch (e: Exception) {
                                    null
                                }
                            }
                            // Check if the Bitmap is not null
                            if (selectedFile != null) {
                                // Convert Bitmap to ByteArray
                                val requestBuilder = selectedFile?.let {
                                    RequestBody.create("image/*".toMediaTypeOrNull(),
                                        it
                                    )
                                }

                                // Create the MultipartBody.Part
                                val imageBody = requestBuilder?.let {
                                    MultipartBody.Part.createFormData("profile_image", selectedFile.name,
                                        it
                                    )
                                }
                                // Get a reference to Firebase Storage
                                val storage = FirebaseStorage.getInstance()
                                val storageRef = storage.reference

                                // Initialize shared preferences
                                val userId = postItemViewModel.userId
                                val uniqueId = UUID.randomUUID().toString()

                                // Generate a unique name for the image file using the current timestamp
                                val fileName = "item_${System.currentTimeMillis()}.jpg"
                                val imageRef = storageRef.child("item_images/$userId/$uniqueId/$fileName")

                                val image = "item_images/$userId/$uniqueId/$fileName"
                                val existingImageRef = storageRef.child("item_images/$userId")

                                existingImageRef.listAll().addOnSuccessListener { listResult ->
                                    val allTasks = mutableListOf<Task<Void>>()
                                    listResult.items.forEach { item ->
                                        val deleteTask = item.delete()
                                        allTasks.add(deleteTask)
                                    }

                                    Tasks.whenAllComplete(allTasks).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("FirebaseStorage", "All images inside folder deleted successfully")
                                            // Upload the image to Firebase Storage
                                            val uploadTask = imageRef.putFile(Uri.fromFile(selectedFile))

                                            uploadTask.continueWithTask { task ->
                                                if (!task.isSuccessful) {
                                                    task.exception?.let {
                                                        throw it
                                                    }
                                                }
                                                imageRef.downloadUrl
                                            }.addOnCompleteListener { task ->
                                                if (task.isSuccessful) {
                                                    val imageUrl = task.result.toString()
                                                    Toast.makeText(context, "Image upload success", Toast.LENGTH_SHORT).show()

                                                } else {
                                                    // Handle unsuccessful image upload
                                                    Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
                                                }
                                            }

                                            uploadTask.addOnFailureListener { exception ->
                                                Log.e("FirebaseStorage", "Upload failed: ${exception.message}")
                                                Toast.makeText(context, "${exception.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            Log.e("FirebaseStorage", "Failed to delete images inside folder: ${task.exception}")
                                        }
                                    }
                                }.addOnFailureListener { exception ->
                                    Log.e("FirebaseStorage", "Failed to list items in folder: $exception")
                                }


                                postItemViewModel.postItem(
                                    name, description, location, amount, 2, status, image
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


                            }


                        } catch (e: Exception) {
                            Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                            Log.d("Login","${e.message}")
                            // You can also log the exception for debugging purposes
                            e.printStackTrace()
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


@Composable
fun ItemImage(bitmap: Bitmap?, contentDescription: String?, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(120.dp)
            .background(Color.LightGray)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}


