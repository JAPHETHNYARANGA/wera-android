package com.werrah.wera.navigation

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.werrah.wera.presentation.viewModel.DeleteAccountViewModel
import com.werrah.wera.presentation.viewModel.DeleteListingViewModel
import com.werrah.wera.presentation.viewModel.FavoritesViewModel
import com.werrah.wera.presentation.viewModel.GetCategoriesViewModel
import com.werrah.wera.presentation.viewModel.GetIndividualItemViewModel
import com.werrah.wera.presentation.viewModel.GetListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserListingsViewModel
import com.werrah.wera.presentation.viewModel.GetUserViewModel
import com.werrah.wera.presentation.viewModel.LogoutViewModel
import com.werrah.wera.presentation.viewModel.MessagesViewModel
import com.werrah.wera.presentation.viewModel.PostItemViewModel
import com.werrah.wera.presentation.viewModel.RemoveFromFavoritesViewModel
import com.werrah.wera.presentation.viewModel.UpdateProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(postItemViewModel : PostItemViewModel,
               sharedPreferences : SharedPreferences,
               getListingsViewModel : GetListingsViewModel,
                updateProfileViewModel: UpdateProfileViewModel,
               getUserViewModel : GetUserViewModel,
               getUserListingsViewModel : GetUserListingsViewModel,
               getIndividualItemViewModel: GetIndividualItemViewModel,
               deleteListingViewModel : DeleteListingViewModel,
               getCategoriesViewModel : GetCategoriesViewModel,
               logoutViewModel :LogoutViewModel,
               deleteAccountViewModel: DeleteAccountViewModel,
               messagesViewModel :MessagesViewModel,
               favoritesViewModel :FavoritesViewModel,
               removeFromFavoritesViewModel :RemoveFromFavoritesViewModel
){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        modifier = Modifier.fillMaxWidth(),
        bottomBar = {
            if (currentRoute != BottomBarScreen.CreateMessage.route) {
                BottomBar(navController = navController)
            }
        }
            ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            BottomNavGraph(navController = navController,
                postItemViewModel = postItemViewModel,
                sharedPreferences = sharedPreferences,
                getListingsViewModel = getListingsViewModel,
                updateProfileViewModel = updateProfileViewModel,
                getUserViewModel = getUserViewModel,
                getUserListingsViewModel = getUserListingsViewModel,
                getIndividualItemViewModel = getIndividualItemViewModel,
                deleteListingViewModel = deleteListingViewModel,
                getCategoriesViewModel = getCategoriesViewModel,
                logoutViewModel = logoutViewModel,
                deleteAccountViewModel = deleteAccountViewModel,
                messagesViewModel = messagesViewModel,
                favoritesViewModel =favoritesViewModel,
                removeFromFavoritesViewModel = removeFromFavoritesViewModel
            )

        }
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.CreateListing,
        BottomBarScreen.Messages,
//        BottomBarScreen.MapItem,
        BottomBarScreen.Profile,
    )

    val navStackBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackStackEntry?.destination

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment =  Alignment.CenterVertically
        ) {
            screens.forEach{ screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )

            }
        }
    }
}

@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    val selected = currentDestination?.hierarchy?.any{ it.route == screen.route } == true
    val purple500 =  Color(0xFF6200EE)
    val background =
        if (selected) purple500.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(modifier = Modifier
        .height(40.dp)
        .clip(CircleShape)
        .background(background)
        .clickable(onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        })){
        Row(modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon( painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )
        }
    }
}


