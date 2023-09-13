package com.example.wera.navigation

import com.example.wera.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon:Int,
    val icon_focused:Int
) {
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.home,
        icon_focused = R.drawable.home
    )

        object Favorite: BottomBarScreen(
        route = "favorite",
        title = "Favorite",
        icon = R.drawable.add,
        icon_focused = R.drawable.add
    )

    object Messages: BottomBarScreen(
        route = "message",
        title = "Message",
        icon = R.drawable.message,
        icon_focused = R.drawable.message
    )

    object CreateMessage : BottomBarScreen(
//        route = "createMessage/{receiverId}",
        route = "createMessage/{receiverId}",
        title = "Create Message",
        icon = R.drawable.message,
        icon_focused = R.drawable.message
    )

    object Profile: BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.profile,
        icon_focused = R.drawable.profile,
    )
    object OtherProfile: BottomBarScreen(
        route = "OtherProfile",
        title = "Profile",
        icon = R.drawable.profile,
        icon_focused = R.drawable.profile,
    )

    object Edit : BottomBarScreen(
        route ="edit",
        title = "Edit",
        icon = R.drawable.add,
        icon_focused = R.drawable.add
    )

    object IndividualItem : BottomBarScreen(
        route ="individualItem",
        title = "individualItem",
        icon = R.drawable.add,
        icon_focused = R.drawable.add
    )

    object MapItem : BottomBarScreen(
        route ="map",
        title = "map",
        icon = R.drawable.location,
        icon_focused = R.drawable.location
    )
}