package com.example.wera.presentation.views.Authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wera.MainActivity
import com.example.wera.R
import com.example.wera.presentation.viewModel.ForgetPasswordViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.LoginUserViewModel
import com.example.wera.presentation.views.shared.LoadingSpinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    loginUserViewModel: LoginUserViewModel,
    forgetPasswordViewModel: ForgetPasswordViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var showForgotPassword by remember { mutableStateOf(false) }
    var forgetPassword by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    val sharedPreferences = context.applicationContext.getSharedPreferences(
        "loginPreference",
        Context.MODE_PRIVATE
    )

    val preventBackAction = remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    // Intercept the back button press
    BackHandler(enabled = preventBackAction.value) {
        // Empty block to prevent the back action
    }

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Forgot Password?",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                showForgotPassword = true
                            }
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Button(
                    onClick = {

                        if(email.isNotEmpty() && password.isNotEmpty()){
                            isLoading = true
                            CoroutineScope(Dispatchers.Main).launch {
                                try {
                                    loginUserViewModel.loginUser(
                                        email, password
                                    )?.let { response ->
                                        Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()

                                        if (response.success) {

                                            val editor = sharedPreferences.edit()
                                            editor.putString("loginPreference", response.token)
                                            editor.commit()

                                            val userId = sharedPreferences.edit()
                                            userId.putString("userIdPreference", response.user.userId)
                                            userId.commit()

                                            // Close the application and restart it
                                            (context as? MainActivity)?.restartApplication()


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
                    Text(text = "Login")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? Register here",
                    style = TextStyle(fontWeight = FontWeight.Bold),

                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            navController.navigate("register")
                        }

                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (showForgotPassword) {
            Dialog(
                onDismissRequest = { showForgotPassword = false },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { showForgotPassword = false },
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color(0xFF1A202C)
                                )
                            }
                        }
                        OutlinedTextField(
                            value = forgetPassword,
                            onValueChange = { forgetPassword = it },
                            label = { Text("Enter your email") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )

                        Button(
                            onClick = {
                                if(forgetPassword.isNotEmpty()){
                                    isLoading = true
                                    CoroutineScope(Dispatchers.Main).launch {
                                        try {
                                            forgetPasswordViewModel.forgotPassword(
                                                forgetPassword
                                            )?.let { response ->
                                                Toast.makeText(context, response.status, Toast.LENGTH_LONG).show()

                                                if (response.success) {
                                                    showForgotPassword = false // Hide the popup

                                                } else {
                                                    Toast.makeText(context, response.status, Toast.LENGTH_LONG).show()
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
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color(0xFF1A202C)
                            )
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }

        // Loading spinner overlay
        if (isLoading) {
            LoadingSpinner(isLoading = true)
        }
    }


}


// Custom VisualTransformation to hide the password
class PasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val replacementChar = '•'
        val transformedText = buildAnnotatedString {
            repeat(text.length) {
                append(replacementChar)
            }
        }
        return TransformedText(transformedText, OffsetMapping.Identity)
    }
}

