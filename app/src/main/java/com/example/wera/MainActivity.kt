package com.example.wera

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wera.navigation.MainScreen
import com.example.wera.presentation.viewModel.DeleteAccountViewModel
import com.example.wera.presentation.viewModel.DeleteListingViewModel
import com.example.wera.presentation.viewModel.GetCategoriesViewModel
import com.example.wera.presentation.viewModel.GetIndividualItemViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.GetUserListingsViewModel
import com.example.wera.presentation.viewModel.GetUserViewModel
import com.example.wera.presentation.viewModel.LoginUserViewModel
import com.example.wera.presentation.viewModel.LogoutViewModel
import com.example.wera.presentation.viewModel.MessagesViewModel
import com.example.wera.presentation.viewModel.PostItemViewModel
import com.example.wera.presentation.viewModel.RegisterUserViewModel
import com.example.wera.presentation.viewModel.UpdateProfileViewModel
import com.example.wera.presentation.views.Authentication.LoginScreen
import com.example.wera.presentation.views.Authentication.RegisterScreen
import com.example.wera.presentation.views.Contents.Profile.EditProfile
import com.example.wera.ui.theme.WeraTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginUserViewModel : LoginUserViewModel by viewModels()
    private val registerUserViewModel : RegisterUserViewModel by viewModels()
    private val postItemViewModel : PostItemViewModel by viewModels()
    private val getListingsViewModel : GetListingsViewModel by viewModels()
    private val updateProfileViewModel : UpdateProfileViewModel by viewModels()
    private val getUserViewModel : GetUserViewModel by viewModels()
    private val getUserListingsViewModel : GetUserListingsViewModel by viewModels()
    private val getIndividualItemViewModel : GetIndividualItemViewModel by viewModels()
    private val deleteListingViewModel : DeleteListingViewModel by viewModels()
    private val getCategoriesViewModel : GetCategoriesViewModel by viewModels()
    private val logoutViewModel : LogoutViewModel by viewModels()
    private val deleteAccountViewModel : DeleteAccountViewModel by viewModels()
    private val messagesViewModel : MessagesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val preferences = getSharedPreferences("userIdPreference", Context.MODE_PRIVATE)
                    Toast.makeText(this,"${ preferences.getString("userIdPreference", "")}", Toast.LENGTH_LONG).show()
                    val navController = rememberNavController()
                    val sharedPreferences = getSharedPreferences("loginPreference", Context.MODE_PRIVATE)
                    val loginToken = getLoginToken(sharedPreferences)


                    NavHost(navController = navController, startDestination = if (loginToken != null) "home" else "login") {
                        composable("home") {
                            MainScreen(postItemViewModel, sharedPreferences,  getListingsViewModel, updateProfileViewModel, getUserViewModel, getUserListingsViewModel, getIndividualItemViewModel
                            , deleteListingViewModel, getCategoriesViewModel , logoutViewModel, deleteAccountViewModel, messagesViewModel)
                        }

                        composable("edit") {
                            EditProfile(navController,updateProfileViewModel, getUserViewModel)
                        }

                        composable("login") {
                            LoginScreen(loginUserViewModel, navController)
                        }

                        composable("register") {
                            RegisterScreen(registerUserViewModel, navController)
                        }
                    }
                }
            }
        }
    }
    private fun getLoginToken(sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString("loginPreference", null)
    }
}

