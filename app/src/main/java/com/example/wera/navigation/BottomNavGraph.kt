package com.example.wera.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wera.presentation.viewModel.DeleteAccountViewModel
import com.example.wera.presentation.viewModel.DeleteListingViewModel
import com.example.wera.presentation.viewModel.GetCategoriesViewModel
import com.example.wera.presentation.viewModel.GetIndividualItemViewModel
import com.example.wera.presentation.viewModel.GetListingsViewModel
import com.example.wera.presentation.viewModel.GetUserListingsViewModel
import com.example.wera.presentation.viewModel.GetUserViewModel
import com.example.wera.presentation.viewModel.LogoutViewModel
import com.example.wera.presentation.viewModel.MessagesViewModel
import com.example.wera.presentation.viewModel.PostItemViewModel
import com.example.wera.presentation.viewModel.UpdateProfileViewModel
import com.example.wera.presentation.views.Contents.FavoritesScreen
import com.example.wera.presentation.views.Contents.HomeScreen
import com.example.wera.presentation.views.Contents.IndividualItemPage
import com.example.wera.presentation.views.Contents.MessagesScreen
import com.example.wera.presentation.views.Contents.Profile.EditProfile
import com.example.wera.presentation.views.Contents.Profile.ProfilePage


@Composable
fun BottomNavGraph(
    navController : NavHostController,
    postItemViewModel : PostItemViewModel,
    sharedPreferences : SharedPreferences,
    getListingsViewModel: GetListingsViewModel,
    updateProfileViewModel: UpdateProfileViewModel,
    getUserViewModel : GetUserViewModel,
    getUserListingsViewModel : GetUserListingsViewModel,
    getIndividualItemViewModel : GetIndividualItemViewModel,
    deleteListingViewModel: DeleteListingViewModel,
    getCategoriesViewModel: GetCategoriesViewModel,
    logoutViewModel :LogoutViewModel,
    deleteAccountViewModel: DeleteAccountViewModel,
    messagesViewModel : MessagesViewModel
){

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            HomeScreen(navController, getListingsViewModel, getIndividualItemViewModel)
        }

        composable(route =BottomBarScreen.Favorite.route)
        {
            FavoritesScreen(postItemViewModel, navController, getListingsViewModel,  getUserListingsViewModel, getCategoriesViewModel)
        }

        composable(route =BottomBarScreen.Messages.route)
        {
            MessagesScreen(messagesViewModel)
        }

        composable(route =BottomBarScreen.Profile.route)
        {
            ProfilePage(navController , sharedPreferences, getUserViewModel , getUserListingsViewModel, deleteListingViewModel , getListingsViewModel, logoutViewModel, deleteAccountViewModel  )
        }

        composable(route = BottomBarScreen.Edit.route)
        {
            EditProfile(navController, updateProfileViewModel, getUserViewModel)
        }

        composable(route = BottomBarScreen.IndividualItem.route)
        {
            IndividualItemPage(navController , sharedPreferences, getIndividualItemViewModel )
        }
    }

}
