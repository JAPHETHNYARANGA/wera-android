package com.werrah.wera.navigation

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.werrah.wera.presentation.viewModel.DeleteAccountViewModel
import com.werrah.wera.presentation.viewModel.DeleteListingViewModel
import com.werrah.wera.presentation.viewModel.GetCategoriesViewModel
import com.werrah.wera.presentation.viewModel.GetIndividualItemViewModel
import com.werrah.wera.presentation.viewModel.GetListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserViewModel
import com.werrah.wera.presentation.viewModel.LogoutViewModel
import com.werrah.wera.presentation.viewModel.MessagesViewModel
import com.werrah.wera.presentation.viewModel.PostItemViewModel
import com.werrah.wera.presentation.viewModel.UpdateProfileViewModel
import com.werrah.wera.presentation.views.Contents.FavoritesScreen
import com.werrah.wera.presentation.views.Contents.HomeScreen
import com.werrah.wera.presentation.views.Contents.IndividualItemPage
import com.werrah.wera.presentation.views.Contents.Location.location
import com.werrah.wera.presentation.views.Contents.Messages.createMessagesPage
import com.werrah.wera.presentation.views.Contents.MessagesScreen
import com.werrah.wera.presentation.views.Contents.Profile.EditProfile
import com.werrah.wera.presentation.views.Contents.Profile.OtherProfile
import com.werrah.wera.presentation.views.Contents.Profile.ProfilePage


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

        composable(route =BottomBarScreen.CreateListing.route)
        {
            FavoritesScreen(postItemViewModel, navController, getListingsViewModel,  getUserListingsViewModel, getCategoriesViewModel)
        }

        composable(route =BottomBarScreen.Messages.route)
        {
            MessagesScreen(navController, messagesViewModel, updateProfileViewModel)
        }

        composable(route = BottomBarScreen.CreateMessage.route) {
            createMessagesPage(navController, messagesViewModel)
        }

        composable(route =BottomBarScreen.Profile.route)
        {
            ProfilePage(navController , sharedPreferences, getUserViewModel , getUserListingsViewModel, deleteListingViewModel , getListingsViewModel, logoutViewModel, deleteAccountViewModel  )
        }

        composable(route = BottomBarScreen.OtherProfile.route)
        {
            OtherProfile(navController, updateProfileViewModel)
        }

        composable(route = BottomBarScreen.Edit.route)
        {
            EditProfile(navController, updateProfileViewModel, getUserViewModel)
        }

        composable(route = BottomBarScreen.IndividualItem.route)
        {
            IndividualItemPage(navController ,  getIndividualItemViewModel, messagesViewModel )
        }

        composable(route = BottomBarScreen.MapItem.route)
        {
            location()
        }
    }

}
