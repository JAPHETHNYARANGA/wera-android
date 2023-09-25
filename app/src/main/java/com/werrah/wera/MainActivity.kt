package com.werrah.wera

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.werrah.wera.navigation.MainScreen
import com.werrah.wera.presentation.viewModel.DeleteAccountViewModel
import com.werrah.wera.presentation.viewModel.DeleteListingViewModel
import com.werrah.wera.presentation.viewModel.FavoritesViewModel
import com.werrah.wera.presentation.viewModel.ForgetPasswordViewModel
import com.werrah.wera.presentation.viewModel.GetCategoriesViewModel
import com.werrah.wera.presentation.viewModel.GetIndividualItemViewModel
import com.werrah.wera.presentation.viewModel.GetListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserViewModel
import com.werrah.wera.presentation.viewModel.LoginUserViewModel
import com.werrah.wera.presentation.viewModel.LogoutViewModel
import com.werrah.wera.presentation.viewModel.MessagesViewModel
import com.werrah.wera.presentation.viewModel.PostItemViewModel
import com.werrah.wera.presentation.viewModel.RegisterUserViewModel
import com.werrah.wera.presentation.viewModel.RemoveFromFavoritesViewModel
import com.werrah.wera.presentation.viewModel.UpdateProfileViewModel
import com.werrah.wera.presentation.views.Authentication.LoginScreen
import com.werrah.wera.presentation.views.Authentication.RegisterScreen
import com.werrah.wera.presentation.views.Contents.Profile.EditProfile
import com.werrah.wera.ui.theme.WeraTheme
//import com.google.firebase.crashlytics.FirebaseCrashlytics
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
    private val forgetPasswordViewModel : ForgetPasswordViewModel by viewModels()
    private val favoritesViewModel : FavoritesViewModel by viewModels()
    private val removeFromFavoritesViewModel : RemoveFromFavoritesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val navController = rememberNavController()
                    val sharedPreferences = getSharedPreferences("loginPreference", Context.MODE_PRIVATE)
                    val loginToken = getLoginToken(sharedPreferences)


                    NavHost(navController = navController, startDestination = if (loginToken != null) "home" else "login") {
                        composable("home") {
                            MainScreen(postItemViewModel, sharedPreferences,  getListingsViewModel, updateProfileViewModel, getUserViewModel, getUserListingsViewModel, getIndividualItemViewModel
                            , deleteListingViewModel, getCategoriesViewModel , logoutViewModel, deleteAccountViewModel, messagesViewModel, favoritesViewModel, removeFromFavoritesViewModel)
                        }

                        composable("edit") {
                            EditProfile(navController,updateProfileViewModel, getUserViewModel)
                        }

                        composable("login") {
                            LoginScreen(loginUserViewModel, forgetPasswordViewModel, navController)
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

    fun restartApplication() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent)
        System.exit(0)
    }

}

