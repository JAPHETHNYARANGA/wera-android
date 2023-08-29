package com.example.wera.presentation.views.Authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wera.MainActivity
import com.example.wera.R
import com.example.wera.presentation.viewModel.LoginUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    loginUserViewModel: LoginUserViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    val sharedPreferences = context.applicationContext.getSharedPreferences(
        "loginPreference",
        Context.MODE_PRIVATE
    )

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
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            Button(
                onClick = {
                          if(email.isNotEmpty() && password.isNotEmpty()){
                              CoroutineScope(Dispatchers.Main).launch {
                                  try {
                                      loginUserViewModel.loginUser(
                                          email, password
                                      )?.let { response ->
                                          Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()

                                          if (response.success) {

                                              val editor = sharedPreferences.edit()
                                              editor.putString("loginPreference", response.token)
                                              editor.apply()

                                              val userId = sharedPreferences.edit()
                                              userId.putString("userIdPreference", response.user.userId)
                                              userId.apply()

                                              val intent = Intent(context, MainActivity::class.java)
                                              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                              context.startActivity(intent)


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