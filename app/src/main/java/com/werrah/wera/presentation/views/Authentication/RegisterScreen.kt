package com.werrah.wera.presentation.views.Authentication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.werrah.wera.R
import com.werrah.wera.presentation.viewModel.RegisterUserViewModel
import com.werrah.wera.presentation.views.shared.LoadingSpinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    registerUserViewModel: RegisterUserViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val sharedPreferences = context.applicationContext.getSharedPreferences(
        "loginPreference",
        Context.MODE_PRIVATE
    )
    var isLoading by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Werrah",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = "Work without limits",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
                Image(painter = painterResource(id = R.drawable.worker), contentDescription ="Worker image" )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Username") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),

                    )
                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1A202C),
                        unfocusedBorderColor = Color(0xFF1A202C)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            val eyeIcon = if (passwordVisibility) R.drawable.ic_eye_off else R.drawable.ic_eye
                            Icon(
                                painter = painterResource(id = eyeIcon),
                                contentDescription = if (passwordVisibility) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Button(
                    onClick = {
                        if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                            isLoading = true
                            CoroutineScope(Dispatchers.Main).launch {
                                try {
                                    registerUserViewModel.registerUser(
                                        name,
                                        email, password
                                    )?.let { response ->
                                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                                        if (response.success) {
                                            navController.navigate("login")
                                        } else {
                                            Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                                    Log.d("Login","${e.message}")
                                    // You can also log the exception for debugging purposes
                                    e.printStackTrace()
                                }finally {
                                    isLoading = false
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(text = "Register")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? Login here",
                    style = TextStyle(fontWeight = FontWeight.Bold),

                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            navController.navigate("login")
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (isLoading) {
            LoadingSpinner(isLoading = true)
        }
    }

}


