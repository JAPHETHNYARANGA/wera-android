package com.example.wera.presentation.views.Contents.Profile

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wera.R
import com.example.wera.navigation.BottomBarScreen
import com.example.wera.presentation.viewModel.GetUserViewModel
import com.example.wera.presentation.viewModel.UpdateProfileViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavController, updateProfileViewModel: UpdateProfileViewModel, getUserViewModel:GetUserViewModel){

    // Get the user data from the ViewModel
    val userData by getUserViewModel.userDisplay.collectAsState()

    // Initialize state variables with user data
    var name by remember { mutableStateOf(userData?.name ?: "") }
    var email by remember { mutableStateOf(userData?.email ?: "") }
    var phone by remember { mutableStateOf(userData?.phone ?: "") }
    var bio by remember { mutableStateOf(userData?.bio ?: "") }
    var occupation by remember { mutableStateOf(userData?.occupation ?: "") }

    val keyboardController = LocalSoftwareKeyboardController.current

    var imageSelected by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current


    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    // Create a CoroutineScope for managing coroutines
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(end = 10.dp, start = 10.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Edit Profile", style = TextStyle(fontWeight = FontWeight.Bold))
        }
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
                        CircularImage(
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Column() {
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
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )

                OutlinedTextField(
                    value = occupation,
                    onValueChange = { occupation = it },
                    label = { Text("Occupation") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = {

                        // Check if all required fields are not empty
                        if (name.isNotEmpty() && email.isNotEmpty() && bio.isNotEmpty() && occupation.isNotEmpty()) {
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
                                val userId = updateProfileViewModel.userId

                                // Generate a unique name for the image file using the current timestamp
                                val fileName = "profile_${System.currentTimeMillis()}.jpg"
                                val imageRef = storageRef.child("profile_images/$userId/$fileName")

                                val profile = "profile_images/$userId/$fileName"
                                val existingImageRef = storageRef.child("profile_images/$userId")

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

                                // Send the API request to update the user profile with the image
                                scope.launch {
                                    try {
                                        val response = imageBody?.let {
                                            updateProfileViewModel.updateProfile(
                                                name, email, phone, bio, occupation, profile
                                            )
                                        }
                                        // Handle the API response here (e.g., show toast message)
                                        Toast.makeText(context, response!!.message, Toast.LENGTH_LONG).show()
                                        if (response.success) {
                                            navController.navigate(BottomBarScreen.Profile.route)
                                            getUserViewModel.fetchProfile()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                                        Log.d("Profile Update", "${e.message}")
                                        e.printStackTrace()
                                    }
                                }
                            }
                        } else {
                            // Show a message indicating that all required fields must be filled
                            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_LONG).show()
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Update")
                    }
                }
            }

        }

    }
}


@Composable
fun CircularImage(bitmap: Bitmap?, contentDescription: String?, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
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

